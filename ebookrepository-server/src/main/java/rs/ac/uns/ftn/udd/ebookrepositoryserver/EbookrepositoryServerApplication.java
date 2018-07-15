package rs.ac.uns.ftn.udd.ebookrepositoryserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Category;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.EBook;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.Language;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.model.User;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.CategoryService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.EBookService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.LanguageService;
import rs.ac.uns.ftn.udd.ebookrepositoryserver.service.UserService;

@SpringBootApplication
public class EbookrepositoryServerApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private EBookService eBookService;

	public static void main(String[] args) {
		SpringApplication.run(EbookrepositoryServerApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws Exception {

        Category category1 = new Category("SILVER");
        Category category2 = new Category("GOLD");
        Category category3 = new Category("PLATINUM");
        categoryService.save(category1);
        categoryService.save(category2);
        categoryService.save(category3);
        
        Language language1 = new Language("Srpski jezik");
        Language language2 = new Language("Engleski jezik");
        Language language3 = new Language("Nemacki jezik");
        languageService.save(language1);
        languageService.save(language2);
        languageService.save(language3);
        
        User user1 = new User("Ivana", "Savin", "ivana.unitedforce@gmail.com", "ivana", "PRETPLATNIK", category1);
        userService.save(user1);


        EBook eBook1 = new EBook(0, "Ana Karenjina", "Lav Nikolajevic Tolstoj", "Ana Karenjina, Grof Vronski, Ljevin", 1877, "Ana Karenjina", "pdf", category1, language1, user1);
        EBook eBook2 = new EBook(1, "Sto godina samoce", "Gabrijel Garsija Markes", "Sto godina samoce", 1967, "Sto godina samoce", "pdf", category1, language1, user1);
        EBook eBook3 = new EBook(2, "Lovac u zitu", "Dzerom Dejvid Selindzer", "Lovac u zitu", 1951, "Lovac u zitu", "pdf", category1, language2, user1);
        eBookService.save(eBook1);
        eBookService.save(eBook2);
        eBookService.save(eBook3);
      
        Iterable<EBook> books = eBookService.findAll();

        books.forEach(x -> System.out.println(x));


    }

}
