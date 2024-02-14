package com.example.internetshop.controllers;
import com.example.internetshop.controllers.servicesDTO.BookingsOfClientDTO;
import com.example.internetshop.controllers.servicesDTO.ClientsDTO;
import com.example.internetshop.controllers.servicesDTO.OrderPositionDTO;
import com.example.internetshop.models.Basket;
import com.example.internetshop.models.Booking;
import com.example.internetshop.models.Client;
import com.example.internetshop.models.OrderPosition;
import com.example.internetshop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    ClientRepository clientRepository;
    public ClientController(ClientRepository clientRepository)
    { this.clientRepository = clientRepository;}

    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    OrderPositionsRepository orderPositionsRepository;

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    BasketRepository basketRepository;

    @PostMapping
    public ResponseEntity<?> addClient (@RequestBody Client client) {
        int inn = client.getClientINN();
        Optional<Client> byINN = clientRepository.findById(inn);
        if (byINN.isPresent()) {
            return new ResponseEntity<>
            ("Client is already exist", HttpStatus.BAD_REQUEST);
        }
        clientRepository.save(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllClientsDTO () {
        List<ClientsDTO> clients = new ArrayList<>();

        for (Client client : clientRepository.findAll()) {
            ClientsDTO clientDTO = new ClientsDTO();
            clientDTO.setClientINN(client.getClientINN());
            clientDTO.setName(client.getName());
            clientDTO.setFamilyName(client.getFamilyName());
            clientDTO.setBirthDate(client.getBirthDate());
            clientDTO.setRegDate(client.getRegDate());
            clients.add(clientDTO);
        }
        return new ResponseEntity<>(clients,HttpStatus.OK);
    }

    @GetMapping("/{clientINN}")
    public ResponseEntity<?> getClientByINN (@PathVariable int clientINN) {
        if (clientRepository.findById(clientINN).isEmpty()) {
            return new ResponseEntity<>
            ("Client with such INN doesn't exist",HttpStatus.NOT_FOUND);
        }
        Client requestedClient = clientRepository.findById(clientINN).get();
        return new ResponseEntity<>(requestedClient,HttpStatus.OK);
    }

    @DeleteMapping("/{clientINN}")
    public ResponseEntity<?> deleteClientByINN (@PathVariable int clientINN) {
        if (clientRepository.findById(clientINN).isEmpty()) {

            return new ResponseEntity<>
            ("Client with such INN doesn't exist",HttpStatus.NOT_FOUND);
        }
        clientRepository.delete(clientRepository.findById(clientINN).get());
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
    }

    @GetMapping ("/{clientINN}/bookings")
    public ResponseEntity<?> getBookingsByClientDTO (@PathVariable int clientINN) {
        List <BookingsOfClientDTO> result = new ArrayList<>();
        int totalSumOfAllPositions = 0;

        if (clientRepository.findById(clientINN).isEmpty()) {
            return new ResponseEntity<>
             ("Client with such INN doesn't exist",HttpStatus.BAD_REQUEST);
        }

        List<Booking> bookingslist = clientRepository.findById(clientINN).
                get().getBookings();

        for (Booking book : bookingslist) {
            BookingsOfClientDTO bookingsOfClientDTO = new BookingsOfClientDTO();
            bookingsOfClientDTO.setClientINN(clientINN);
            bookingsOfClientDTO.setBookingDate(book.getBookingDate());

            List <OrderPositionDTO> list = new ArrayList<>();
            totalSumOfAllPositions = 0;
            for (OrderPosition o : book.getPositions()) {
                OrderPositionDTO orderPositionDTO = new OrderPositionDTO();
                orderPositionDTO.setPositionName(o.getGoods().getGoodName());
                orderPositionDTO.setGoodsQuantity(o.getGoodsQuantity());
                orderPositionDTO.setPriceForUnit(o.getGoods().getPrice());
                orderPositionDTO.setSumPriceOfPosition
                         (o.getGoods().getPrice() * o.getGoodsQuantity());
                list.add(orderPositionDTO);
                totalSumOfAllPositions += orderPositionDTO.getSumPriceOfPosition();
            }
            bookingsOfClientDTO.setPositionsList(list);
            bookingsOfClientDTO.setBookingTotalPrice(totalSumOfAllPositions);
            result.add(bookingsOfClientDTO);
       }
       return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/{clientID}/createOrder")
    public ResponseEntity<?> transformBasketInOrder (@PathVariable int clientID) {
        Client client = clientRepository.findById(clientID).get();
        List <Basket> baskets = client.getBaskets();
        Booking booking = new Booking();
        booking.setClient(client);
        booking.setBookingDate(LocalDateTime.now().toLocalDate());
        bookingsRepository.save(booking);

        for (Basket basket : baskets) {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setGoods(basket.getGood());
            orderPosition.setId(basket.getGood().getGoodID());
            orderPosition.setGoodsQuantity(basket.getGoodQuantity());
            orderPosition.setBooking(booking);
            orderPositionsRepository.save(orderPosition);
        }
        return new ResponseEntity<>("Order confirmed",HttpStatus.OK);

    }


    @PostMapping("/bulk")
    public ResponseEntity <?> createClients (@RequestBody List <Client> list) {
        List <Client> successfullyCreated = new ArrayList<>();
        for (Client client : list) {
            Optional<Client> byINN = clientRepository.findById(client.getClientINN());
            if (byINN.isEmpty()) {
                clientRepository.save(client);
                successfullyCreated.add(client);
            }
        }
        return new ResponseEntity<>(successfullyCreated,HttpStatus.CREATED);
    }






}




