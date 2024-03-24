package com.foodway.api.service.establishment;

import com.foodway.api.apiclient.MapsClient;
import com.foodway.api.apiclient.SimpleMail;
import com.foodway.api.apiclient.SimpleMailClient;
import com.foodway.api.controller.UserController;
import com.foodway.api.handler.exceptions.EstablishmentNotFoundException;
import com.foodway.api.model.*;
import com.foodway.api.model.Enums.EEntity;
import com.foodway.api.model.Enums.ESearchFilter;
import com.foodway.api.model.Enums.ETypeRate;
import com.foodway.api.record.DTOs.*;
import com.foodway.api.record.DTOs.GMaps.MapsLongLag;
import com.foodway.api.record.RequestUserEstablishment;
import com.foodway.api.record.UpdateEstablishmentData;
import com.foodway.api.record.UpdateEstablishmentPersonal;
import com.foodway.api.record.UpdateEstablishmentProfile;
import com.foodway.api.repository.*;
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
    private MapsClient mapsClient;
    @Autowired
    private SimpleMailClient simpleMailClient;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CulinaryRepository culinaryRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private UpvoteRepository upvoteRepository;
    @Autowired
    private CommentRepository commentRepository;

    public ResponseEntity<List<Establishment>> validateIsEmpty(List<Establishment> establishments) {
        if (establishments.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No content");
        }
        return ResponseEntity.status(200).body(establishments);
    }

    public ResponseEntity<List<Establishment>> getEstablishments() {
        List<Establishment> establishments = establishmentRepository.findAll();
        return validateIsEmpty(establishments);
    }

    public ResponseEntity<EstablishmentProfileDTO> getEstablishmentProfile(UUID idEstablishment) {
        Establishment establishment = getEstablishment(idEstablishment).getBody();
        List<Comment> comments = establishment.getPostList();

        long qtdUpvotes = upvoteRepository.countByIdEstablishment(idEstablishment);
        long qtdRates = rateRepository.countByIdEstablishment(idEstablishment);

        EstablishmentProfileDTO establishmentProfileDTO = new EstablishmentProfileDTO(
                establishment.getName(),
                establishment.getEstablishmentName(),
                establishment.getCulinary().get(0).getName(),
                establishment.getEmail(),
                establishment.getPhone(),
                establishment.getGeneralRate(),
                establishment.getFoodRate(),
                establishment.getAmbientRate(),
                establishment.getServiceRate(),
                establishment.getAddress().getLatitude(),
                establishment.getAddress().getLongitude(),
                qtdUpvotes,
                establishment.getPostList().size(),
                qtdRates,
                comments,
                establishment.getProfileHeaderImg()
        );
        return ResponseEntity.status(200).body(establishmentProfileDTO);
    }

//    private List<CommentEstablishmentProfileDTO> createCommentDTO(List<Comment> comments) {
//        List<CommentEstablishmentProfileDTO> commentDTOs = new ArrayList<>();
//
//        for (Comment comment : comments) {
//            commentDTOs.add(new CommentEstablishmentProfileDTO(
//                    comment.getIdPost(),
//                    comment.getUserPhoto(),
//                    comment.getComment(),
//                    comment.getGeneralRate(),
//                    comment.getUpvoteList().size(),
//                    comment.getReplies()
//                )
//            );
//        }
//        return commentDTOs;
//    }

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
        if (establishments.isEmpty()) {
            establishments = establishmentRepository.findAll();
        }
        return ResponseEntity.ok(establishments);
    }

    public ResponseEntity<ListaObj<Establishment>> getEstablishmentOrderByRate() {
        List<Establishment> establishments = getEstablishments().getBody();
        ListaObj<Establishment> list = new ListaObj<>(establishments.size(), establishments);
        return ResponseEntity.status(200).body(list.filterBySome(list, "rate", EEntity.ESTABLISHMENT));
    }

    public ResponseEntity<Establishment> getEstablishment(UUID paramId) {
        Establishment establishment = establishmentRepository.findById(paramId).orElseThrow(() -> new EstablishmentNotFoundException("Establishment not found"));
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
        MapsLongLag mapsLongLag = mapsClient.getLongLat(address.number(), address.street(), address.city(), "AIzaSyAKELgmqf4j5kRAdn9EKTC28cMao0sQvJE");
        establishment.getAddress().setLatitude(mapsLongLag.results().get(0).geometry().location().lat());
        establishment.getAddress().setLongitude(mapsLongLag.results().get(0).geometry().location().lng());

        Establishment establishmentSaved = establishmentRepository.save(establishment);

        SimpleMail simpleMail = new SimpleMail(establishment.getName(), establishment.getEstablishmentName(), establishment.getEmail(), establishment.getTypeUser());

        try {
            simpleMailClient.aaa("/account-created", simpleMail);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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


        if (establishment.getStatusCode().value() == 404) {
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
            gravaArquivoTxt(establishments, archiveName + ".txt");
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
            leArquivoTxt(archiveName + ".txt");
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    public void getAverageOfIndicators(Establishment establishment) {
        Map<String, Double> current = rateRepository.getIndicators(ETypeRate.AMBIENT, ETypeRate.SERVICE, ETypeRate.FOOD, establishment.getIdUser());

        establishment.setRates(current);
    }

    public ResponseEntity<List<Establishment>> getEstablishmentsByCulinary(Integer idCulinary) {
        List<Establishment> establishments;
        if(idCulinary == 999){
            establishments = establishmentRepository.findAll();
        }else {
             establishments = establishmentRepository.findEstablishmentByCulinary_Id(idCulinary);
        }
        return validateIsEmpty(establishments);
    }

    public ResponseEntity<Establishment> patchEstablishmentProfile(UUID id, UpdateEstablishmentProfile establishment) {
        Optional<Establishment> establishment1 = establishmentRepository.findById(id);
        if (establishment1.isEmpty()) {
            throw new EstablishmentNotFoundException("Establishment not found");
        }
        Establishment establishment2 = establishment1.get();
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(establishment.emailActual());
        userLoginDto.setPassword(establishment.passwordActual());
        ResponseEntity<UserTokenDto> userTokenDtoResponseEntity = userController.login(userLoginDto);

        establishment2.updateProfileEstablishment(Optional.of(establishment));

        if (userTokenDtoResponseEntity.getStatusCodeValue() == 200) {
            return ResponseEntity.status(200).body(establishmentRepository.save(establishment2));
        } else {
            System.out.println("Erro ao atualizar");
        }
        return ResponseEntity.status(401).build();

    }

    public ResponseEntity<Establishment> patchEstablishmentPersonal(UUID id, UpdateEstablishmentPersonal establishmentUpdate) {

        Establishment establishment = getEstablishment(id).getBody();
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setEmail(establishmentUpdate.emailActual());
        userLoginDto.setPassword(establishmentUpdate.passwordActual());
        ResponseEntity<UserTokenDto> userTokenDtoResponseEntity = userController.login(userLoginDto);

        establishment.updatePersonalEstablishment(Optional.of(establishmentUpdate));
        if (userTokenDtoResponseEntity.getStatusCode() == HttpStatusCode.valueOf(200)) {
            return ResponseEntity.status(200).body(establishmentRepository.save(establishment));
        } else {
            System.out.println("Erro ao atualizar");
        }
        return ResponseEntity.status(401).build();
    }

    public ResponseEntity<List<SearchEstablishmentDTO>> searchAllEstablishments(UUID idSession, String establishmentName, ESearchFilter filter) {
        List<Establishment> establishments;
        if (establishmentName != null  && filter != null) {
            establishments = switch (filter) {
                case COMMENTS ->
                        establishmentRepository.findByEstablishmentNameContainsIgnoreCaseOrderByPostListDesc(establishmentName);
                case RELEVANCE ->
                        establishmentRepository.findByEstablishmentNameContainsIgnoreCaseOrderByGeneralRateDesc(establishmentName);
                case UPVOTES ->
                        establishmentRepository.findByEstablishmentNameContainsIgnoreCaseOrderByPostList_UpvoteListDesc(establishmentName);
            };
        } else if (filter != null) {
            establishments = switch (filter) {
                case COMMENTS -> establishmentRepository.findAllByOrderByPostListSizeDesc();
                case RELEVANCE -> establishmentRepository.findByOrderByGeneralRateDesc() ;
                case UPVOTES -> establishmentRepository.findByOrderByPostList_UpvoteListDesc();
            };
        } else {
            establishments = establishmentRepository.findAll();
        }
        validateIsEmpty(establishments);
        List<SearchEstablishmentDTO> searchEstablishmentDTOs = new ArrayList<>();

        for (Establishment establishment : establishments) {
            boolean isFavorite = favoriteRepository.existsByIdCustomerAndIdEstablishment(idSession, establishment.getIdUser());
            SearchEstablishmentDTO searchEstablishmentDTO = getSearchEstablishmentDTO(establishment, isFavorite);
            searchEstablishmentDTOs.add(searchEstablishmentDTO);
        }

        return ResponseEntity.status(200).body(searchEstablishmentDTOs);
    }

    @NotNull
    private SearchEstablishmentDTO getSearchEstablishmentDTO(Establishment establishment, boolean isFavorite) {
        int sizeCulinary = establishment.getCulinary().size();
        int sizeComment = establishment.getPostList().size();
        final long countUpvotes = establishmentRepository.countByPostList_UpvoteList_IdEstablishment(establishment.getIdUser());
        String culinary;
        String comment;
        if (sizeCulinary == 0 || establishment.getCulinary().get(sizeCulinary - 1).getName() == null) {
            culinary = "Nenhuma culinária";
        } else {
            culinary = establishment.getCulinary().get(0).getName();
        }
        if (sizeComment == 0 || establishment.getPostList().get(sizeComment - 1).getComment() == null) {
            comment = "Nenhum comment";
        } else {
            comment = establishment.getPostList().get(sizeComment - 1).getComment();
        }
        return new SearchEstablishmentDTO(
                establishment.getIdUser(),
                establishment.getEstablishmentName(),
                establishment.getTypeUser(),
                culinary,
                establishment.getGeneralRate(),
                establishment.getDescription(),
                countUpvotes,
                establishment.getProfilePhoto(),
                establishment.getAddress().getLatitude(),
                establishment.getAddress().getLongitude(),
                establishment.getPostList().size(),
                comment,
                isFavorite);
    }

    public ResponseEntity<List<RelevanceDTO>> getEstablishmentsByRelevance(String culinary) {
        List<Establishment> establishments = establishmentRepository.findTop10ByCulinary_NameIgnoreCaseOrderByGeneralRate(culinary);
        validateIsEmpty(establishments);

        List<RelevanceDTO> relevanceDTOS = establishments.stream().map(establishment -> new RelevanceDTO(establishment.getEstablishmentName(), establishment.getProfilePhoto(), establishment.getGeneralRate(), rateRepository.countByIdEstablishment(establishment.getIdUser()))).toList();
        return ResponseEntity.ok(relevanceDTOS);
    }

    public ResponseEntity<List<CommentDTO>> getEstablishmentCommentsByIdEstablishment(UUID idEstablishment) {
        Establishment establishment = getEstablishment(idEstablishment).getBody();
        List<Comment> comments = establishment.getPostList();
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOs.add(new CommentDTO(
                    establishment.getEstablishmentName(),
                    comment.getComment(),
                    comment.getGeneralRate(),
                    comment.getUpvoteList().size()));
        }
        return ResponseEntity.ok(commentDTOs);
    }
}
