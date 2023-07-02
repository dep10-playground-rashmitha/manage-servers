package lk.ijse.dep10.manageservers.repo;

import lk.ijse.dep10.manageservers.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<Server,Long> {
    Server findByIpAddress(String ipAddress);
}
