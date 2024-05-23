//package com.foodway.api.service.upvote;
//
//import com.foodway.api.model.Comment;
//import com.foodway.api.model.Upvote;
//import com.foodway.api.record.RequestUpvote;
//import com.foodway.api.repository.CommentRepository;
//import com.foodway.api.repository.UpvoteRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class UpvoteServiceTest {
//
//    @Mock
//    UpvoteRepository upvoteRepository;
//    @Mock
//    CommentRepository commentRepository;
//    @InjectMocks
//    UpvoteService upvoteService;
//
//    @Test
//    @DisplayName("Should return all upvotes")
//    void getAll() {
//        Mockito.when(upvoteRepository.findAll()).thenReturn(List.of(new Upvote(), new Upvote()));
//        ResponseEntity<List<Upvote>> u = upvoteService.getAll();
//        List<Upvote> upvotes = u.getBody();
//
//        assertNotNull(upvotes);
//        assertEquals(2, upvotes.size());
//        assertEquals(HttpStatus.OK, u.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("Should return exception NO CONTENT if Upvote is empty")
//    void getAllException() {
//        Mockito.when(upvoteRepository.findAll()).thenReturn(Collections.emptyList());
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> upvoteService.getAll().getBody());
//        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("Should return a Upvote by id")
//    void get() {
//        long id = 32L;
//        Upvote upvote = new Upvote();
//        upvote.setIdUpvote(id);
//        Mockito.when(upvoteRepository.findById(upvote.getIdUpvote())).thenReturn(Optional.of(upvote));
//
//        ResponseEntity<Upvote> up = upvoteService.get(id);
//        Upvote u = up.getBody();
//        assertNotNull(u);
//        assertEquals(upvote.getIdUpvote(), u.getIdUpvote());
//        assertEquals(HttpStatus.OK, up.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("Should return a exception NO FOUND when Upvote does not exist by id")
//    void getException() {
//        long id = 32L;
//        Mockito.when(upvoteRepository.findById(id)).thenReturn(Optional.empty());
//
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
//                () -> upvoteService.get(id));
//
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
//    }
////
////    @Test
////    @DisplayName("Should return the Upvote created")
////    void post() {
////        Upvote upvoteComparator = new Upvote();
////        Comment comment = new Comment();
////        UUID id = UUID.fromString("08909178-8da3-11ee-b9d1-0242ac120002");
////        comment.setIdPost(id);
////        upvoteComparator.setIdComment(id);
////        upvoteComparator.setIdCustomer(UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002"));
////        upvoteComparator.setIdEstablishment(UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002"));
////
////        Mockito.when(upvoteRepository.findAll()).thenReturn(Collections.singletonList(upvoteComparator));
////        Mockito.when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
////
////        UUID idEstablishment = UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002");
////        UUID idCustomer = UUID.fromString("2786516c-8da3-11ee-b9d1-0242ac120002");
////        UUID idComment = UUID.fromString("08909178-8da3-11ee-b9d1-0242ac120002");
////
////        RequestUpvote newUpvote = new RequestUpvote(
////                idCustomer,
////                idEstablishment,
////                idComment
////        );
////
////        ResponseEntity<Upvote> u = upvoteService.post(newUpvote);
////        Upvote upvote = u.getBody();
////
////        assertNotNull(upvote);
////        assertEquals(idEstablishment, upvote.getIdEstablishment());
////        assertEquals(idCustomer, upvote.getIdCustomer());
////        assertEquals(idComment, upvote.getIdComment());
////        assertEquals(HttpStatus.CREATED, u.getStatusCode());
////    }
////
////    @Test
////    @DisplayName("Should return a exception BAD REQUEST when Upvote already does exist")
////    void postException() {
////        Upvote upvoteComparator = new Upvote();
////        Comment comment = new Comment();
////        UUID id = UUID.fromString("08909178-8da3-11ee-b9d1-0242ac120002");
////        comment.setIdPost(id);
////        upvoteComparator.setIdComment(id);
////        upvoteComparator.setIdCustomer(UUID.fromString("2786516c-8da3-11ee-b9d1-0242ac120002"));
////        upvoteComparator.setIdEstablishment(UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002"));
////
////        Mockito.when(upvoteRepository.findAll()).thenReturn(Collections.singletonList(upvoteComparator));
////        Mockito.when(commentRepository.findById(upvoteComparator.getIdComment())).thenReturn(Optional.of(comment));
////
////        RequestUpvote newUpvote = new RequestUpvote(
////                UUID.fromString("2786516c-8da3-11ee-b9d1-0242ac120002"),
////                UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002"),
////                UUID.fromString("08909178-8da3-11ee-b9d1-0242ac120002")
////        );
////
////        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
////                () -> upvoteService.post(newUpvote));
////
////        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
////    }
////
////    @Test
////    @DisplayName("Should return Upvote updated")
////    void put() {
////        Upvote upvote = new Upvote();
////        Long id = 32L;
////        upvote.setIdUpvote(id);
////        upvote.setIdComment(UUID.fromString("08909178-8da3-11ee-b9d1-0242ac120002"));
////        upvote.setIdCustomer(UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002"));
////        upvote.setIdEstablishment(UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002"));
////
////        Mockito.when(upvoteRepository.existsById(id)).thenReturn(true);
////        Mockito.when(upvoteRepository.findById(id)).thenReturn(Optional.of(upvote));
////
////        UUID idEstablishment = UUID.fromString("89e757ec-8e35-11ee-b9d1-0242ac120002");
////        UUID idCustomer = UUID.fromString("2786516c-8da3-11ee-b9d1-0242ac120002");
////        UUID idComment = UUID.fromString("9558121a-8e35-11ee-b9d1-0242ac120002");
////
////        RequestUpvote newUpvote = new RequestUpvote(
////                idCustomer,
////                idEstablishment,
////                idComment
////        );
////        ResponseEntity<Upvote> up = upvoteService.put(id, newUpvote);
////        Upvote u = up.getBody();
////        assertNotNull(u);
////        assertEquals(idEstablishment, u.getIdEstablishment());
////        assertEquals(idCustomer, u.getIdCustomer());
////        assertEquals(idComment, u.getIdComment());
////        assertEquals(HttpStatus.OK, up.getStatusCode());
////    }
////
////    @Test
////    @DisplayName("Should return a exception NO FOUND when Upvote does not exist")
////    void putException() {
////        Long id = 32L;
////
////        Mockito.when(upvoteRepository.existsById(id)).thenReturn(false);
////
////        UUID idEstablishment = UUID.fromString("89e757ec-8e35-11ee-b9d1-0242ac120002");
////        UUID idCustomer = UUID.fromString("2786516c-8da3-11ee-b9d1-0242ac120002");
////        UUID idComment = UUID.fromString("9558121a-8e35-11ee-b9d1-0242ac120002");
////
////        RequestUpvote newUpvote = new RequestUpvote(
////                idCustomer,
////                idEstablishment,
////                idComment
////        );
////
////        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
////                () -> upvoteService.put(id, newUpvote));
////
////        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
////
////    }
////
////    @Test
////    @DisplayName("Should return Upvote deleted")
////    void delete() {
////        Upvote upvote = new Upvote();
////        Long id = 32L;
////        upvote.setIdUpvote(id);
////        upvote.setIdComment(UUID.fromString("08909178-8da3-11ee-b9d1-0242ac120002"));
////        upvote.setIdCustomer(UUID.fromString("39c23540-8e2e-11ee-b9d1-0242ac120002"));
////        upvote.setIdEstablishment(UUID.fromString("6cd66f8a-8da4-11ee-b9d1-0242ac120002"));
////
////        Mockito.when(upvoteRepository.existsById(id)).thenReturn(true);
////
////        HttpStatusCode httpStatusCode = upvoteService.delete(id).getStatusCode();
////
////        assertEquals(HttpStatus.OK, httpStatusCode);
////    }
////
////    @Test
////    @DisplayName("Should return a exception NO FOUND when Update does not exist")
////    void deleteException() {
////        Long id = 32L;
////
////        Mockito.when(upvoteRepository.existsById(id)).thenReturn(false);
////
////        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
////                () -> upvoteService.delete(id));
////
////        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
////    }
////
////    @Test
////    @DisplayName("Should return false when Upvote is empty")
////    void existUpvoteIsEmpty() {
////        Upvote upvote = new Upvote();
////        Mockito.when(upvoteRepository.findAll()).thenReturn(Collections.emptyList());
////        boolean response = upvoteService.existUpvote(upvote);
////        assertFalse(response);
////    }
////
////    @Test
////    @DisplayName("Should return false when Upvote does not exist")
////    void existUpvoteDoesNotExist() {
////        Upvote upvoteCompared = new Upvote();
////        upvoteCompared.setIdComment(UUID.fromString("08909178-8da3-11ee-b9d1-0242ac120002"));
////        upvoteCompared.setIdCustomer(UUID.fromString("2786516c-8da3-11ee-b9d1-0242ac120002"));
////        Upvote upvoteComparator = new Upvote();
////        upvoteComparator.setIdComment(UUID.fromString("70dcc242-8da3-11ee-b9d1-0242ac120002"));
////        upvoteComparator.setIdCustomer(UUID.fromString("76e8382e-8da3-11ee-b9d1-0242ac120002"));
////        Mockito.when(upvoteRepository.findAll()).thenReturn(Collections.singletonList(upvoteComparator));
////        boolean response = upvoteService.existUpvote(upvoteCompared);
////        assertFalse(response);
////    }
////
////    @Test
////    @DisplayName("Should return true when Upvote does exist")
////    void existUpvoteDoesExist() {
////        Upvote upvote = new Upvote();
////        upvote.setIdComment(UUID.fromString("08909178-8da3-11ee-b9d1-0242ac120002"));
////        upvote.setIdCustomer(UUID.fromString("2786516c-8da3-11ee-b9d1-0242ac120002"));
////        Upvote upvoteComparator = new Upvote();
////        upvoteComparator.setIdComment(UUID.fromString("08909178-8da3-11ee-b9d1-0242ac120002"));
////        upvoteComparator.setIdCustomer(UUID.fromString("2786516c-8da3-11ee-b9d1-0242ac120002"));
////        Mockito.when(upvoteRepository.findAll()).thenReturn(Collections.singletonList(upvoteComparator));
////        boolean response = upvoteService.existUpvote(upvote);
////        assertTrue(response);
////    }
//}