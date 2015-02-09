package Inventario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Hibernate.Componente;
import Hibernate.Subtipo;
import Hibernate.Tipo;

public class Principal {

	private static Scanner leer = new Scanner(System.in);

	public static void main(String[] args) throws ParseException {

		int menu = -1;

		do {

			try {
				System.out.println("INVENTARIO");
				System.out.println("==========================");
				System.out.println("1-Añadir Componente");
				System.out.println("2-Actualizar Componente");
				System.out.println("3-Borrar Componente");
				System.out.println("4-Buscar Componente");
				System.out.println("0-Salir");
				System.out.println("==========================");
				System.out.println("Seleccione una opcion");
				menu = Integer.parseInt(leer.nextLine());

			} catch (NumberFormatException e) {

				System.out.println("No ha introducido un numero");

			}

			switch (menu) {

			case 1:

				añadirComponente();

				break;

			case 2:

				menuModificarComponente(seleccionarComponente());

				break;

			case 3:

				borrarComponente();

				break;

			case 4:

				menuBuscar();

				break;

			case 0:

				System.out.println("Hasta pronto!");

				break;

			default:

				System.out.println("No ha seleccionado una opcion valida");

				break;
			}

		} while (menu != 0);
	}

	public static void añadirComponente() throws ParseException {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		SimpleDateFormat formaFecha = new SimpleDateFormat("dd-MM-yyyy");
		String nombre = null, descripcion = null, fechaCompra;
		int pvp = 0, precioCoste = 0, stock = 0;
		Date fecha = new Date();

		try {

			do {

				System.out.println("Nombre del componente");
				nombre = leer.nextLine();

			} while (comprobarVacio(nombre));

			do {

				System.out.println("Descripcion del componente");
				descripcion = leer.nextLine();

			} while (comprobarVacio(descripcion));

			System.out.println("Precio coste del componente");
			precioCoste = Integer.parseInt(leer.nextLine());

			System.out.println("Precio venta al publico del componente");
			pvp = Integer.parseInt(leer.nextLine());

			System.out.println("Cuanto stock tiene del componente");
			stock = Integer.parseInt(leer.nextLine());

		} catch (NumberFormatException e) {

			System.out.println("No ha introducido un numero");

		}

		System.out.println("Introduzca la fecha de compra(dd-mm-yyyy)");
		fechaCompra = leer.nextLine();
		fecha = (Date) formaFecha.parse(fechaCompra);

		System.out.println();

		System.out.println("Seleccione el tipo que desea");
		mostrarTipos();
		int idTipo = Integer.parseInt(leer.nextLine());

		System.out.println("Seleccione el subtipo a cual desea añadir");
		mostrarSubtipo(idTipo);
		int idSubtipo = Integer.parseInt(leer.nextLine());

		Subtipo sub = new Subtipo();
		sub = cogerSubtipo(idSubtipo);

		Componente comp = new Componente();
		comp.setNombre(nombre);
		comp.setDescripcion(descripcion);
		comp.setStock(stock);
		comp.setPrecioCoste(precioCoste);
		comp.setPvp(pvp);
		comp.setFechaCompra(fecha);
		comp.setSubtipo(sub);

		sesion.save(comp);
		trans.commit();
		sesion.close();

	}

	public static void mostrarTipos() {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		Tipo tipo = new Tipo();
		Query q = sesion.createQuery("from Tipo");
		Iterator<Tipo> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			tipo = (Tipo) iter.next();
			System.out.println(tipo.getIdTipo() + "-" + tipo.getNombre());
		}
	}

	public static Subtipo mostrarSubtipo(int idTipo) {

		Subtipo sub = new Subtipo();

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		Tipo tipo = new Tipo();
		Query q = sesion.createQuery("from Subtipo where idTipo =" + idTipo);

		Iterator<Subtipo> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			sub = (Subtipo) iter.next();
			System.out.println(sub.getIdSubtipo() + "-" + sub.getNombre());

		}

		return null;

	}

	public static Subtipo cogerSubtipo(int idSubtipo) {

		Subtipo sub = new Subtipo();

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		Tipo tipo = new Tipo();
		Query q = sesion.createQuery("from Subtipo where idSubtipo ="
				+ idSubtipo);

		Iterator<Subtipo> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			sub = (Subtipo) iter.next();

			return sub;

		}

		return null;

	}

	public static void mostrarComponentes(int idSubtipo) {

		Componente comp = new Componente();

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		Query q = sesion.createQuery("from Componente where idSubtipo ="
				+ idSubtipo);

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			comp = (Componente) iter.next();
			System.out.println(comp.getIdComponente() + "-" + comp.getNombre());

		}

	}

	public static Componente cogerComponente(int idComponente) {

		Componente comp = new Componente();

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		Tipo tipo = new Tipo();
		Query q = sesion.createQuery("from Componente where idComponente ="
				+ idComponente);

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			comp = (Componente) iter.next();

			return comp;

		}

		return null;

	}

	public static void borrarComponente() {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		Componente comp = new Componente();

		System.out.println("Seleccione tipo para buscar en los suptipos");
		mostrarTipos();
		int idTipo = Integer.parseInt(leer.nextLine());

		System.out.println("Seleccione el subtipo para ver sus componentes");
		mostrarSubtipo(idTipo);
		int idSubtipo = Integer.parseInt(leer.nextLine());

		System.out.println("Elija el componente que desea eliminar");
		mostrarComponentes(idSubtipo);
		int idComponente = Integer.parseInt(leer.nextLine());
		comp = cogerComponente(idComponente);

		Query q = sesion.createQuery("from Componente where idComponente ="
				+ idComponente);

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			comp = (Componente) iter.next();
			System.out.println(comp.getIdComponente() + "-" + comp.getNombre());

		}

		sesion.delete(comp);
		trans.commit();
		sesion.close();

	}

	public static Componente seleccionarComponente() {

		Componente comp = new Componente();

		System.out.println("Seleccione tipo para buscar en los suptipos");
		mostrarTipos();
		int idTipo = Integer.parseInt(leer.nextLine());

		System.out.println("Seleccione el subtipo para ver sus componentes");
		mostrarSubtipo(idTipo);
		int idSubtipo = Integer.parseInt(leer.nextLine());

		System.out.println("Elija el componente que desea eliminar");
		mostrarComponentes(idSubtipo);
		int idComponente = Integer.parseInt(leer.nextLine());
		comp = cogerComponente(idComponente);

		return comp;

	}

	public static void menuModificarComponente(Componente comp) {

		int menu = -1;

		do {

			try {
				System.out.println("MODIFICAR COMPONENTE");
				System.out.println("=====================");
				System.out.println("1-Cambiar nombre");
				System.out.println("2-Cambiar descripcion");
				System.out.println("3-Cambiar precio venta publico");
				System.out.println("4-Cambiar precio coste");
				System.out.println("5-Cambiar stock");
				System.out.println("6-Cambiar subtipo");
				System.out.println("======================");
				System.out.println("Seleccione una opcion");
				menu = Integer.parseInt(leer.nextLine());

			} catch (NumberFormatException e) {

				System.out.println("No ha introducido un numero");

			}

			switch (menu) {

			case 1:

				cambiarNombre(comp);

				break;

			case 2:

				cambiarDescripcion(comp);

				break;

			case 3:

				cambiarPvp(comp);

				break;

			case 4:

				cambiarPrecioCompra(comp);

				break;

			case 5:

				cambiarStock(comp);

				break;

			case 6:

				cambiarSubtipo(comp);

				break;

			case 0:

				System.out.println("Volviendo al menu anterior");

			default:

				System.out.println("No es una opcion valida");
				break;
			}
		} while (menu != 0);

	}

	public static void cambiarNombre(Componente comp) {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		System.out.println("El nombre actual es: " + comp.getNombre());

		System.out.println("Introduzca el nuevo nombre");
		String nombre = leer.nextLine();

		int id = comp.getIdComponente();
		Query q = sesion.createQuery("from Componente where idComponente ="
				+ id);

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			comp = iter.next();
			comp.setNombre(nombre);

		}

		sesion.update(comp);
		trans.commit();
		sesion.close();

	}

	public static void cambiarPvp(Componente comp) {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		System.out.println("El PVP es: " + comp.getPvp());

		System.out.println("Introduzca el nuevo PVP:");
		int pvp = Integer.parseInt(leer.nextLine());

		Query q = sesion.createQuery("from Componente where idComponente ="
				+ comp.getIdComponente());

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			comp = iter.next();
			comp.setPvp(pvp);

		}

		sesion.update(comp);
		trans.commit();
		sesion.close();

	}

	public static void cambiarDescripcion(Componente comp) {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		System.out.println("La descripcion es: " + comp.getDescripcion());

		System.out.println("Introduzca la nueva descripcion:");
		String descripcion = leer.nextLine();

		Query q = sesion.createQuery("from Componente where idComponente ="
				+ comp.getIdComponente());

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			comp = iter.next();
			comp.setDescripcion(descripcion);

		}

		sesion.update(comp);
		trans.commit();
		sesion.close();

	}

	public static void cambiarPrecioCompra(Componente comp) {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		System.out.println("El precio de compra es: " + comp.getPrecioCoste());

		System.out.println("Introduzca el nuevo precio de compra:");
		int precioCompra = Integer.parseInt(leer.nextLine());

		Query q = sesion.createQuery("from Componente where idComponente ="
				+ comp.getIdComponente());

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			comp = iter.next();
			comp.setPrecioCoste(precioCompra);

		}

		sesion.update(comp);
		trans.commit();
		sesion.close();

	}

	public static void cambiarStock(Componente comp) {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		System.out.println("El stock es: " + comp.getStock());

		System.out.println("Introduzca el stock:");
		int stock = Integer.parseInt(leer.nextLine());

		Query q = sesion.createQuery("from Componente where idComponente ="
				+ comp.getIdComponente());

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			comp = iter.next();
			comp.setStock(stock);

		}

		sesion.update(comp);
		trans.commit();
		sesion.close();

	}

	public static void cambiarSubtipo(Componente comp) {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		System.out.println("El subtipo es: " + comp.getSubtipo().toString());

		System.out.println("Introduzca el tipo:");
		mostrarTipos();
		int idTipo = Integer.parseInt(leer.nextLine());
		System.out.println("Introduzca el subtipo");
		mostrarSubtipo(idTipo);
		int idSubtipo = Integer.parseInt(leer.nextLine());
		Subtipo sub = cogerSubtipo(idSubtipo);

		Query q = sesion.createQuery("from Componente where idComponente ="
				+ comp.getIdComponente());

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		while (iter.hasNext()) {

			comp = iter.next();
			comp.setSubtipo(sub);

		}

		sesion.update(comp);
		trans.commit();
		sesion.close();

	}

	public static void menuBuscar() {

		int menu = -1;

		do {
			
			try {
				
				System.out.println("MENU BUSCAR");
				System.out.println("1.-Tipo");
				System.out.println("2.-Nombre");
				System.out.println("3.-Codigo");
				System.out.println("0.-Salir");
				System.out.println("Seleccione una opcion:");
				menu = Integer.parseInt(leer.nextLine());

			} catch (NumberFormatException e) {

				System.out.println("No ha introducido un numero");

			}
			
			switch (menu) {

			case 1:

				buscarPorTipo();

				break;

			case 2:

				buscarNombre();

				break;

			case 3:

				buscarCodigo();

				break;

			case 0:

				System.out.println("Volviendo al menu anterior");

				break;

			default:

				System.out.println("No es una opcion valida");

				break;

			}

		} while (menu != 0);

	}

	public static void buscarNombre() {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		String nombre;

		System.out.println("Por que nombre desea buscar");
		nombre = leer.nextLine();

		Query q = sesion.createQuery("from Componente where nombre LIKE '%"
				+ nombre + "%'");

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		Componente comp = new Componente();

		if (!iter.hasNext()) {

			System.out.println();
			System.out.println("No corresponde a nada");
			System.out.println();

		}

		while (iter.hasNext()) {

			comp = iter.next();

			System.out.println("======================================");
			System.out.println("Nombre: " + comp.getNombre());
			System.out.println("Descripcion: " + comp.getDescripcion());
			System.out.println("Precio coste: " + comp.getPrecioCoste());
			System.out.println("Precio venta publico: " + comp.getPvp());
			System.out.println("======================================");

		}

	}

	public static void buscarCodigo() {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		int idComponente;

		System.out.println("Por que ID desea buscar");
		idComponente = Integer.parseInt(leer.nextLine());

		Query q = sesion.createQuery("from Componente where idComponente = "
				+ idComponente);

		Iterator<Componente> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		Componente comp = new Componente();

		if (!iter.hasNext()) {

			System.out.println();
			System.out.println("No corresponde a nada");
			System.out.println();

		}

		while (iter.hasNext()) {

			comp = iter.next();

			System.out.println("======================================");
			System.out.println("Nombre: " + comp.getNombre());
			System.out.println("Descripcion: " + comp.getDescripcion());
			System.out.println("Precio coste: " + comp.getPrecioCoste());
			System.out.println("Precio venta publico: " + comp.getPvp());
			System.out.println("======================================");

		}

	}

	public static void buscarPorTipo() {

		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();

		int idTipo;

		System.out.println("Por que ID desea buscar");
		mostrarTipos();
		idTipo = Integer.parseInt(leer.nextLine());

		Query q = sesion
				.createQuery("from Componente as c,Subtipo as sub,Tipo as t "
						+ "WHERE c.subtipo = sub.idSubtipo "
						+ "AND sub.tipo = t.idTipo " + "AND t.idTipo = "
						+ idTipo);

		Iterator<Object> iter;
		q.setFetchSize(10);
		iter = q.iterate();

		Componente comp = new Componente();

		if (!iter.hasNext()) {

			System.out.println();
			System.out.println("No corresponde a nada");
			System.out.println();

		}

		while (iter.hasNext()) {

			Object[] tablas = (Object[]) iter.next();
			comp = (Componente) tablas[0];

			System.out.println("======================================");
			System.out.println("Nombre: " + comp.getNombre());
			System.out.println("Descripcion: " + comp.getDescripcion());
			System.out.println("Precio coste: " + comp.getPrecioCoste());
			System.out.println("Precio venta publico: " + comp.getPvp());
			System.out.println("======================================");

		}

	}

	public static boolean comprobarVacio(String palabra) {

		if (palabra.equals("")) {

			System.out.println("El campo no puede estar vacio");
			return false;
		}

		return true;

	}
}
