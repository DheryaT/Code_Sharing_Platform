package platform;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CodeRepository extends CrudRepository<Snippet, Integer> {
    List<Snippet> findTop10ByOrderByIdDesc();
}
