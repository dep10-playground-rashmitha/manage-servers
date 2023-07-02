package lk.ijse.dep10.manageservers.service.implementation;

import lk.ijse.dep10.manageservers.model.Server;
import lk.ijse.dep10.manageservers.repo.ServerRepo;
import lk.ijse.dep10.manageservers.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

import static java.lang.Boolean.TRUE;
import static lk.ijse.dep10.manageservers.enumeration.Status.SERVER_DOWN;
import static lk.ijse.dep10.manageservers.enumeration.Status.SERVER_UP;

@Service
@Transactional
@Slf4j
public class ServerServiceImplementation implements ServerService {
    private final ServerRepo serverRepo;

    public ServerServiceImplementation(ServerRepo serverRepo) {
        this.serverRepo = serverRepo;
    }

    @Override
    public Server create(Server server) {
        log.info("Saving new Server: {}",server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP: {}",ipAddress);
        Server server = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000)?SERVER_UP:SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching All Servers");
        return serverRepo.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Server get(long id) {
        log.info("Fetching Server By Id: {}",id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating the Server: {}",server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(long id) {
        log.info("Deleting the Server By Id: {}",id);
        serverRepo.deleteById(id);
        return TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames={"server1.jpg","server2.png","server3.png","server4.jpeg"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/img"+imageNames[new Random().nextInt(4)]).
                toUriString();
    }
}
