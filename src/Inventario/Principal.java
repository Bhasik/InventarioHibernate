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
		
		do{
			
		
		System.out.println("INVENTARIO");
		System.out.println("==========================");
		System.out.println("1-Añadir Componente");
		System.out.println("2-Actualizar Componente");
		System.out.println("3-Borrar Componente");
		System.out.println("==========================");
		System.out.println("Seleccione una opcion");
		menu=Integer.parseInt(leer.nextLine());
		
		switch (menu) {
		
			case 1:
				
				añadirComponente();
				
				break;
	
			case 2:
				
				break;
				
			case 3:
				
				break;
				
			case 0:
				
				System.out.println("Hasta pronto!");
				
				break;
				
			default:
				
				System.out.println("No ha seleccionado una opcion valida");
				
				break;
			}
		
		
		
		}while(menu!=0);
	}
	
	
	public static void añadirComponente() throws ParseException{
		
		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();
		
		
		SimpleDateFormat formaFecha = new SimpleDateFormat("dd-MM-yyyy");
		String nombre,descripcion,fechaCompra;
		int pvp,precioCoste,stock;
		Date fecha = new Date();
		
		
		
		
		System.out.println("Nombre del componente");
		nombre = leer.nextLine();
		
		System.out.println("Descripcion del componente");
		descripcion = leer.nextLine();
		
		System.out.println("Precio coste del componente");
		precioCoste = Integer.parseInt(leer.nextLine());
		
		System.out.println("Precio venta al publico del componente");
		pvp = Integer.parseInt(leer.nextLine());
		
		System.out.println("Cuanto stock tiene del componente");
		stock =	Integer.parseInt(leer.nextLine());
		
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
	
	
	public static void mostrarTipos(){
		
		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();
		
		Tipo tipo = new Tipo();
		Query q = sesion.createQuery("from Tipo");
		Iterator<Tipo> iter;
		q.setFetchSize(10);
		iter = q.iterate();
				
				while(iter.hasNext()){
					
					tipo = (Tipo) iter.next();
					System.out.println(tipo.getIdTipo() + "-" + tipo.getNombre());
				}
			}	
	
	public static Subtipo mostrarSubtipo(int idTipo){
		
		Subtipo sub = new Subtipo();
		
		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();
		
		Tipo tipo = new Tipo();
		Query q = sesion.createQuery("from Subtipo where idTipo =" + idTipo);
		
		Iterator<Subtipo> iter;
		q.setFetchSize(10);
		iter = q.iterate();
				
				while(iter.hasNext()){
					
					sub = (Subtipo) iter.next();
					System.out.println(sub.getIdSubtipo() + "-" + sub.getNombre());
					
				}
		
		return null;
		
	}
	
	
	
	public static Subtipo cogerSubtipo(int idSubtipo){
		
		Subtipo sub = new Subtipo();
		
		SessionFactory sesionF = SessionFactoryUtil.getSessionFactory();
		Session sesion = sesionF.openSession();
		Transaction trans = sesion.beginTransaction();
		
		Tipo tipo = new Tipo();
		Query q = sesion.createQuery("from Subtipo where idSubtipo =" + idSubtipo);
		
		Iterator<Subtipo> iter;
		q.setFetchSize(10);
		iter = q.iterate();
				
				while(iter.hasNext()){
					
					sub = (Subtipo) iter.next();
					
					return sub;
					
				}
		
		return null;
		
	}

	
	
	
	
	
}
