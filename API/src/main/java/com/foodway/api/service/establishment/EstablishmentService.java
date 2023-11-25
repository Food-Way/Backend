package com.foodway.api.service.establishment;

import com.foodway.api.controller.UserController;
import com.foodway.api.handler.exceptions.EstablishmentNotFoundException;
import com.foodway.api.model.Enums.EEntity;
import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.MapsClient;
import com.foodway.api.record.DTOs.GMaps.MapsLongLag;
import com.foodway.api.record.DTOs.SearchEstablishmentDTO;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.record.UpdateEstablishmentPersonal;
import com.foodway.api.record.UpdateEstablishmentProfile;
import com.foodway.api.repository.CulinaryRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.RateRepository;
import com.foodway.api.repository.UserRepository;
import com.foodway.api.service.user.authentication.dto.UserLoginDto;
import com.foodway.api.service.user.authentication.dto.UserTokenDto;
import com.foodway.api.utils.ListaObj;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static com.foodway.api.utils.GerenciadorDeArquivo.*;

@Service
public class EstablishmentService {

    @Autowired
    UserController userController;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private MapsClient mapsClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CulinaryRepository culinaryRepository;

    public ResponseEntity<List<Establishment>> validateIsEmpty(List<Establishment> establishments) {
        if (establishments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No content");
        }
        return ResponseEntity.status(200).body(establishments);
    }

    public ResponseEntity<List<Establishment>> getEstablishments() {
        List<Establishment> establishments = establishmentRepository.findAll();
        if (establishments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(establishments);
    }

//    public ResponseEntity<List<Establishment>> getBestEstablishments() {
//        List<Establishment> establishments = establishmentRepository.findTop3ByOrderByGeneralRateDesc();
//        return validateIsEmpty(establishments);
//    }
//
//    public ResponseEntity<List<Establishment>> getBestEstablishmentsByCulinary(String culinary) {
//        List<Establishment> establishments = establishmentRepository.findTop3ByCulinary_NameOrderByGeneralRateDesc(culinary);
//        return validateIsEmpty(establishments);
//    }

    public ResponseEntity<List<Establishment>> getMoreCommentedEstablishments(@Nullable String culinary) {
        List<Establishment> establishments;
        if (culinary == null || culinary.isEmpty()) {
            establishments = establishmentRepository.findTop10ByOrderByPostListDesc();
        } else {
            establishments = establishmentRepository.findTop10ByCulinary_NameOrderByPostListDesc(culinary);
        }
        return ResponseEntity.ok(establishments);
    }

    public ResponseEntity<ListaObj<Establishment>> getEstablishmentOrderByRate() {
        List<Establishment> establishments = getEstablishments().getBody();
        ListaObj<Establishment> list = new ListaObj<>(establishments.size(), establishments);
        return ResponseEntity.status(200).body(list.filterBySome(list, "rate", EEntity.ESTABLISHMENT));
    }

    public ResponseEntity<Establishment> getEstablishment(UUID paramId) {
        Establishment establishment = establishmentRepository.findById(paramId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Establishment not found"));
        getAverageOfIndicators(establishment);
        return ResponseEntity.status(200).body(establishment);
    }

    public ResponseEntity getBinarySearch(Double rate) {
        ListaObj<Establishment> list = getEstablishmentOrderByRate().getBody();
        return ResponseEntity.status(200).body(list.findByRate(rate));
    }

    public ResponseEntity<Establishment> deleteEstablishment(UUID id) {
        Optional<Establishment> establishment = establishmentRepository.findById(id);
        if (establishment.isEmpty()) {
            throw new EstablishmentNotFoundException("Establishment not found");
        }
        establishmentRepository.delete(establishment.get());
        return ResponseEntity.status(200).build();
    }

    public ResponseEntity<Establishment> saveEstablishment(RequestUserEstablishment establishmentRequest) {
        Establishment establishment = new Establishment(establishmentRequest);

        RequestUserEstablishment.Address address = establishmentRequest.address();
        MapsLongLag mapsLongLag = mapsClient.getLongLat(address.number(), address.street(), address.city(), "AIzaSyBdmmGVqp3SOAYkQ8ef1SN9PDBkm8JjD_s");
        establishment.getAddress().setLatitude(mapsLongLag.results().get(0).geometry().location().lat());
        establishment.getAddress().setLongitude(mapsLongLag.results().get(0).geometry().location().lng());
        Establishment establishmentSaved = establishmentRepository.save(establishment);
        return ResponseEntity.status(201).body(establishmentSaved);
    }

    public ResponseEntity<Establishment> putEstablishment(UUID id, UpdateEstablishmentData data) {
        ResponseEntity<Establishment> establishment1 = getEstablishment(id);
        Establishment establishment = establishment1.getBody();
        establishment.update(Optional.of(data));
        return ResponseEntity.status(200).body(establishmentRepository.save(establishment));
    }

    public ResponseEntity<ListaObj<Establishment>> exportEstablishments(String archiveType, UUID id) {
        ResponseEntity<Establishment> establishment = getEstablishment(id);


        if(establishment.getStatusCode().value() == 404) {
            throw new EstablishmentNotFoundException("Establishment not found");
        }

        List<Establishment> establishments = new ArrayList();
        establishments.add(establishment.getBody());

        ListaObj<Establishment> listaObjEstablishments = new ListaObj<>(1, establishments);
        String archiveName = String.valueOf(id);

        if (archiveType.equals("csv")) {
            gravaArquivoCsv(listaObjEstablishments, archiveName);
            return ResponseEntity.ok().build();
        } else if (archiveType.equals("txt")) {
            gravaArquivoTxt(establishments, archiveName+".txt");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<ListaObj<Establishment>> importEstablishments(String archiveType, UUID id) {
        String archiveName = String.valueOf(id);

        if (archiveType.equals("csv")) {
            leArquivoCsv(archiveName);
            return ResponseEntity.ok().build();
        } else if (archiveType.equals("txt")) {
            leArquivoTxt(archiveName+".txt");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public void getAverageOfIndicators(Establishment establishment) {
        Map<String, Double> current = rateRepository.getIndicators(ETypeRate.AMBIENT, ETypeRate.SERVICE, ETypeRate.FOOD, establishment.getIdUser());

        establishment.setRates(current);
    }

    public ResponseEntity<List<Establishment>> getEstablishmentsByCulinary(int idCulinary) {
        List<Establishment> establishments = establishmentRepository.findEstablishmentByCulinary_Id(idCulinary);
        return validateIsEmpty(establishments);
    }

    public ResponseEntity<Establishment> patchEstablishmentProfile(UUID id, UpdateEstablishmentProfile establishment) {
        Optional<Establishment> establishment1 = establishmentRepository.findById(id);
        if (establishment1.isEmpty()) {
            throw new EstablishmentNotFoundException("Establishment not found");
        }
        Establishment establishment2 = establishment1.get();
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(establishment.email());
        userLoginDto.setPassword(establishment.password());
        ResponseEntity<UserTokenDto> userTokenDtoResponseEntity = userController.login(userLoginDto);

        establishment2.updateProfileEstablishment(Optional.of(establishment));

        if (userTokenDtoResponseEntity.getStatusCodeValue() == 200) {
            return ResponseEntity.status(200).body(establishmentRepository.save(establishment2));
        } else {
            System.out.println("Erro ao atualizar");
        }
        return ResponseEntity.status(401).build();

    }

    public ResponseEntity<Establishment> patchEstablishmentPersonal(UUID id, UpdateEstablishmentPersonal establishment) {
        Optional<Establishment> establishment1 = establishmentRepository.findById(id);
        if (establishment1.isEmpty()) {
            throw new EstablishmentNotFoundException("Establishment not found");
        }
        Establishment establishment2 = establishment1.get();
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(establishment.email());
        userLoginDto.setPassword(establishment.password());
        ResponseEntity<UserTokenDto> userTokenDtoResponseEntity = userController.login(userLoginDto);
        establishment2.updatePersonalEstablishment(Optional.of(establishment));
        if (userTokenDtoResponseEntity.getStatusCode() == HttpStatusCode.valueOf(200)) {
            return ResponseEntity.status(200).body(establishmentRepository.save(establishment2));
        } else {
            System.out.println("Erro ao atualizar");
        }
        return ResponseEntity.status(401).build();
    }

    public ResponseEntity<List<SearchEstablishmentDTO>> searchAllEstablishments(@Nullable String establishmentName) {
        List<Establishment> establishments = establishmentName != null ? establishmentRepository.findByEstablishmentNameContainsIgnoreCase(establishmentName) : establishmentRepository.findAll();
        validateIsEmpty(establishments);
        List<SearchEstablishmentDTO> searchEstablishmentDTOs = new ArrayList<>();
        for (Establishment establishment : establishments) {
            SearchEstablishmentDTO searchEstablishmentDTO = getSeachEstablishmentDTO(establishment);
            searchEstablishmentDTOs.add(searchEstablishmentDTO);
        }

        return ResponseEntity.status(200).body(searchEstablishmentDTOs);
    }

    @NotNull
    private SearchEstablishmentDTO getSeachEstablishmentDTO(Establishment establishment) {
        int sizeCulinary = establishment.getCulinary().size();
        int sizeComment = establishment.getPostList().size();
        final long countUpvotes = establishmentRepository.countByPostList_UpvoteList_IdEstablishment(establishment.getIdUser());
        String culinary = null;
        String comment = null;
        if (sizeCulinary == 0 || establishment.getCulinary().get(sizeCulinary-1).getName() == null) {
            culinary = "Nenhuma culinária";
        } else {
            culinary = establishment.getCulinary().get(0).getName();
        }
        if (sizeComment == 0 || establishment.getPostList().get(sizeComment-1).getComment() == null) {
            comment = "Nenhum comment";
        } else {
            comment = establishment.getPostList().get(sizeComment-1).getComment();
        }
        return new SearchEstablishmentDTO(establishment.getIdUser(),establishment.getEstablishmentName(), culinary, establishment.getGeneralRate(), establishment.getDescription(), countUpvotes, establishment.getProfilePhoto(), establishment.getAddress().getLatitude(), establishment.getAddress().getLongitude(), comment);
    }
}
