package it.unicam.pawm.c3;

import it.unicam.pawm.c3.merce.*;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.*;
import it.unicam.pawm.c3.vendita.MerceVendita;
import it.unicam.pawm.c3.vendita.StatoConsegna;
import it.unicam.pawm.c3.vendita.VenditaSpedita;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class C3Application {

	public static void main(String[] args) {
		SpringApplication.run(C3Application.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(ClienteRepository clienteRepository,
										RuoloRepository ruoloRepository,
										NegozioRepository negozioRepository,
										MerceRepository merceRepository,
										MerceAlPubblicoRepository merceAlPubblicoRepository,
										MerceInventarioNegozioRepository merceInventarioNegozioRepository,
										PromozioneRepository promozioneRepository){
		return args -> {

			/*********Parte del personale********************/

			Cliente cliente1 = new Cliente("Andrea", "Rossi", "andrearossi@gmail.com", "rossi");
			Cliente cliente2 = new Cliente("Davide", "Bianchi", "davidebianchi@gmail.com", "bianchi");
			Cliente cliente3 = new Cliente("Alberto", "Neri", "albertoneri@gmail.com", "neri");
			Cliente cliente4 = new Cliente("Mario", "Rossi", "mariorossi@gmail.com", "rossi");
			clienteRepository.saveAll(List.of(cliente1,cliente2,cliente3,cliente4));

			Amministratore admin = new Amministratore(RuoloSistema.AMMINISTRATORE);
			cliente1.setRuolo(admin);
			Commerciante commerciante = new Commerciante(RuoloSistema.COMMERCIANTE);
			cliente2.setRuolo(commerciante);
			Commerciante commerciante1 = new Commerciante(RuoloSistema.COMMERCIANTE);
			cliente3.setRuolo(commerciante1);
			Corriere corriere = new Corriere(RuoloSistema.CORRIERE,"Bartolini", "Via Piazza", "24143251");
			cliente4.setRuolo(corriere);
			ruoloRepository.saveAll(List.of(admin, commerciante,commerciante1,corriere));
			clienteRepository.saveAll(List.of(cliente1,cliente2,cliente3,cliente4));

			Negozio negozio = new Negozio("MadStore","Via Palmiro Togliatti", "2141234314", List.of(Categoria.ABBIGLIAMENTO));
			negozio.addAddettoNegozio(commerciante);
			negozio.addCorriere(corriere);
			negozioRepository.save(negozio);

			Negozio negozio1 = new Negozio("Jeans & Co", "Via Campiglione", "3525235", List.of(Categoria.ABBIGLIAMENTO));
			negozio1.addAddettoNegozio(commerciante1);
			negozioRepository.save(negozio1);

			Merce merce = new Merce("Ipad", Categoria.TECNOLOGIA, "ipad terza generazione");
			merceRepository.save(merce);
			MerceAlPubblico merceAlPubblico = new MerceAlPubblico(999, merce);
			merceAlPubblico.setPromozione(LocalDate.now(),LocalDate.now().plusDays(40),10,45);
			merceAlPubblicoRepository.save(merceAlPubblico);
			MerceInventarioNegozio min = new MerceInventarioNegozio(20, merceAlPubblico);
			merceInventarioNegozioRepository.save(min);
			negozio.addMerceInventarioNegozio(min);
			negozioRepository.save(negozio);
		};
	}
}
