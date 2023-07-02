package lk.ijse.dep10.manageservers.service;

import lk.ijse.dep10.manageservers.model.Server;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;

public interface ServerService {
     Server create(Server server);
     Server ping(String ipAddress) throws IOException;
     Collection<Server> list(int limit);
     Server get(long id);
     Server update(Server server);
     Boolean delete(long id);
}
