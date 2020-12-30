package manj.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import manj.springframework.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Category findByName(String name);

}
