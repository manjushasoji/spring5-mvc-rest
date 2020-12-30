package manj.springframework.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import manj.springframework.domain.Category;
import manj.springframework.repositories.CategoryRepository;

@Component
public class BootStrap implements CommandLineRunner {

	private CategoryRepository categoryRepository;

	public BootStrap(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		Category fruits = new Category();
		fruits.setName("Fruits");
		
		Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");
        
        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);
        
        System.out.println("Data Loaded :"+ categoryRepository.count());

	}

}
