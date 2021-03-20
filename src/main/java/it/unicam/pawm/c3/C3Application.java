package it.unicam.pawm.c3;

import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.merce.Merce;
import it.unicam.pawm.c3.merce.MerceAlPubblico;
import it.unicam.pawm.c3.merce.MerceInventarioNegozio;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.Cliente;
import it.unicam.pawm.c3.personale.Corriere;
import it.unicam.pawm.c3.vendita.MerceVendita;
import it.unicam.pawm.c3.vendita.VenditaSpedita;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
										PromozioneRepository promozioneRepository,
										VenditaSpeditaRepository venditaSpeditaRepository,
										VenditaRepository venditaRepository,
										MerceVenditaRepository merceVenditaRepository,
										CorriereRepository corriereRepository){
		return args -> {

			/*********Parte del personale********************/


//			Cliente cliente1 = new Cliente("Andrea", "Rossi", "andrearossi@gmail.com", "rossi");
//			Cliente cliente2 = new Cliente("Davide", "Bianchi", "davidebianchi@gmail.com", "bianchi");
//			Cliente cliente3 = new Cliente("Alberto", "Neri", "albertoneri@gmail.com", "neri");
//			Cliente cliente4 = new Cliente("Mario", "Rossi", "mariorossi@gmail.com", "rossi");
//			clienteRepository.saveAll(List.of(cliente1,cliente2,cliente3,cliente4));
//
//			Amministratore admin = new Amministratore(RuoloSistema.AMMINISTRATORE);
//			cliente1.setRuolo(admin);
//			Commerciante commerciante = new Commerciante(RuoloSistema.COMMERCIANTE);
//			cliente2.setRuolo(commerciante);
//			Commerciante commerciante1 = new Commerciante(RuoloSistema.COMMERCIANTE);
//			cliente3.setRuolo(commerciante1);
//			Corriere corriere = new Corriere(RuoloSistema.CORRIERE,"Bartolini", "Via Piazza", "24143251");
//			cliente4.setRuolo(corriere);
//			ruoloRepository.saveAll(List.of(admin, commerciante,commerciante1,corriere));
//			clienteRepository.saveAll(List.of(cliente1,cliente2,cliente3,cliente4));

//			Negozio negozio = new Negozio("MadStore","Via Palmiro Togliatti", "2141234314", Set.of(Categoria.ABBIGLIAMENTO));
//			negozioRepository.save(negozio);
//
//			Negozio negozio1 = new Negozio("Jeans & Co", "Via Campglione", "3525235", Set.of(Categoria.ABBIGLIAMENTO));
//			negozioRepository.save(negozio1);

//			Merce merce = new Merce("Iphone", Categoria.TECNOLOGIA, "iphone di dodicesima generazione");
//			merceRepository.save(merce);
//			MerceAlPubblico merceAlPubblico = new MerceAlPubblico(799, merce);
//			merceAlPubblicoRepository.save(merceAlPubblico);
//			MerceInventarioNegozio min = new MerceInventarioNegozio(20, merceAlPubblico);
//			merceInventarioNegozioRepository.save(min);
//			Negozio negozio = negozioRepository.findAll().get(0);
//			negozio.addMerceInventarioNegozio(min);
//			negozioRepository.save(negozio);
//			List<MerceVendita> merceVenditaList = new ArrayList<>();
//			MerceVendita mv = new MerceVendita(12,3,merceAlPubblico);
//			merceVenditaRepository.save(mv);
//			merceVenditaList.add(mv);
//			VenditaSpedita vs = new VenditaSpedita(32,"bella cacca",merceVenditaList);
//			venditaSpeditaRepository.save(vs);
//			Cliente cliente = clienteRepository.findAll().get(0);
//			cliente.addAcquisto(vs);
//			clienteRepository.save(cliente);
//			venditaSpeditaRepository.save(vs);
//			negozio.addVendita(vs);
//			negozio.addVenditaInNegozioRitiro(vs);
//			negozioRepository.save(negozio);
//			venditaSpeditaRepository.save(vs);
//			Corriere corriere = corriereRepository.findAll().get(0);
//			corriere.addMerceDaSpedire(vs);
//			corriereRepository.save(corriere);
//			venditaSpeditaRepository.save(vs);


//			Merce merce1 = new Merce("Ipad", Categoria.TECNOLOGIA, "ipad terza generazione");
//			merceRepository.save(merce1);
//			MerceAlPubblico merceAlPubblico1 = new MerceAlPubblico(999, merce1);
//			merceAlPubblicoRepository.save(merceAlPubblico1);
//			MerceInventarioNegozio min1 = new MerceInventarioNegozio(20, merceAlPubblico1);
//			merceInventarioNegozioRepository.save(min1);
//			Negozio negozio = negozioRepository.findAll().get(0);
//			negozio.addMerceInventarioNegozio(min1);
//			negozioRepository.save(negozio);
//			List<MerceVendita> merceVenditaList1 = new ArrayList<>();
//			MerceVendita mv1 = new MerceVendita(12,3,merceAlPubblico1);
//			merceVenditaList1.add(mv1);
//			merceVenditaRepository.save(mv1);
//			VenditaSpedita vs1 = new VenditaSpedita(32,"bella merda",merceVenditaList1);
//			vs1.setStatoConsegna(StatoConsegna.RITIRATO);
//			venditaSpeditaRepository.save(vs1);
//			Cliente cliente = clienteRepository.findAll().get(0);
//			cliente.addAcquisto(vs1);
//			clienteRepository.save(cliente);
//			venditaSpeditaRepository.save(vs1);
//			Negozio negozio = negozioRepository.findAll().get(0);
//			negozio.addVendita(vs1);
//			negozio.addVenditaInNegozioRitiro(vs1);
//			negozioRepository.save(negozio);
//			venditaSpeditaRepository.save(vs1);
//			Corriere corriere = corriereRepository.findAll().get(0);
//			corriere.addMerceDaSpedire(vs1);
//			corriereRepository.save(corriere);
//			venditaSpeditaRepository.save(vs1);



//			Merce merce2 = new Merce("Macbook", Categoria.TECNOLOGIA, "macbook m1");
//			merceRepository.save(merce2);
//			MerceAlPubblico merceAlPubblico2 = new MerceAlPubblico(1599, merce2);
//			merceAlPubblicoRepository.save(merceAlPubblico2);
//			MerceInventarioNegozio min2 = new MerceInventarioNegozio(10, merceAlPubblico2);
//			merceInventarioNegozioRepository.save(min2);
//			Negozio negozio = negozioRepository.findAll().get(0);
//			negozio.addMerceInventarioNegozio(min2);
//			negozioRepository.save(negozio);
//			negozioRepository.findAll().get(0).addMerceInventarioNegozio(min2);
//			List<MerceVendita> merceVenditaList2 = new ArrayList<>();
//			MerceVendita mv2 = new MerceVendita(12,1,merceAlPubblico2);
//			merceVenditaList2.add(mv2);
//			merceVenditaRepository.save(mv2);
//			VenditaSpedita vs2 = new VenditaSpedita(1599,"bella mercula",merceVenditaList2);
//			vs2.setStatoConsegna(StatoConsegna.CONSEGNATO_AL_NEGOZIO);
//			venditaSpeditaRepository.save(vs2);
//			Cliente cliente = clienteRepository.findAll().get(0);
//			cliente.addAcquisto(vs2);
//			clienteRepository.save(cliente);
//			venditaSpeditaRepository.save(vs2);
//			Negozio negozio = negozioRepository.findAll().get(0);
//			negozio.addVendita(vs2);
//			negozio.addVenditaInNegozioRitiro(vs2);
//			negozioRepository.save(negozio);
//			venditaSpeditaRepository.save(vs2);
//			Corriere corriere = corriereRepository.findAll().get(0);
//			corriere.addMerceDaSpedire(vs2);
//			corriereRepository.save(corriere);
//			venditaSpeditaRepository.save(vs2);


		};
	}
}
