// rabbitmq80-b5ebb0c0247a915d.elb.us-east-1.amazonaws.com

package br.ufs.dcomp.ChatRabbitMQ;
import java.io.IOException;
import br.ufs.dcomp.ChatRabbitMQ.Enviar;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
//import java.io.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;

public class Mainchat {

	//public static final String username = "franciscols";
	//public static final String passwd = "01020304";
	//public static final String url = "ec2-18-208-214-33.compute-1.amazonaws.com";
	
    protected static String destinatario;
    private static String user;
	public static String user1 = "";
	public static boolean pri;
    private static boolean privado = true;
    private static ByteString test;

    
    
    // metodos para verificar se é usuario ou grupo e/ou comandos afins 
    
    private static boolean comandos(String msg) throws Exception, IOException, TimeoutException{
        
        //se a menssagem for para grupo 
        
        if(msg.length() > 0 && msg.substring(0, 1).equals("#")){
        	destinatario = msg.substring(1, msg.length());
        	privado = false; 
        	return true;
        }
		//se for para usuario
        if(msg.length() > 0 && msg.substring(0, 1).equals("@")){ 
            destinatario = msg.substring(1, msg.length());
            privado = true;
            return true;
        }
        
		//Para a criação do grupo
        if(msg.length() > 8 && msg.substring(0, 1).equals("!") && msg.substring(1, msg.indexOf(" ")).equalsIgnoreCase("addGroup") ){
        	String g = msg.substring(msg.indexOf(" ") + 1, msg.length());
        	
			// criação e adicionando ao grupo
            Grupo.createGrupo(g);
            Grupo.addUser(user , g);
            //System.out.println("Criando Grupo: " +g);
            return true;
        }
		//adicionando usuario ao grupo
        if(msg.length() > 14 && msg.substring(0, 1).equals("!") && msg.substring(1, msg.indexOf(" ")).equalsIgnoreCase("addUsertoGroup")){
        	String u = msg.substring(msg.indexOf(" ") + 1, msg.lastIndexOf(" "));
        	String g = msg.substring(msg.lastIndexOf(" ") + 1, msg.length());
        	//adicionar ao grupo 
            Grupo.addUser(u,g);
            //System.out.println("Adicionando usuario " +u +" ao grupo: " +g);
            return true;
        }
		//deletando um grupo
        if(msg.length() > 8 && msg.substring(0, 1).equals("!") && msg.substring(1, msg.indexOf(" ")).equalsIgnoreCase("removeGroup")){
        	String g = msg.substring(msg.indexOf(" ") + 1, msg.length());
        	//deletando o grupo
            Grupo.deleteGrupo(g);
            //System.out.println("Removendo Grupo: " +g);
            return true;
        }
		//removendo o usuario do grupo
        if(msg.length() > 12 && msg.substring(0, 1).equals("!") && msg.substring(1, msg.indexOf(" ")).equalsIgnoreCase("delFromGroup")){
        	String g = msg.substring(msg.indexOf(" ") + 1, msg.lastIndexOf(" "));
        	String u = msg.substring(msg.lastIndexOf(" ") + 1, msg.length());
        	    //tirando o usuario do grupo
                Grupo.DeleteFromGrupo(u, g);
                //System.out.println("Removendo usuario: " +u +" do grupo: " +g);
            return true;
        }
        //metodo para enviar arquivo !upload
        if(msg.length()>7 && msg.substring(0,1).equals("!") && msg.substring(1,msg.indexOf(" ")).equalsIgnoreCase("upload")){
            String caminho = msg.substring(msg.indexOf(" ") + 1, msg.length());
            //System.out.println("Caminho: " + caminho);
             //Grupo.lerUrldoArquivo(caminho);
            
            return true;
        }
        // metodo para lista usuarios do grupo
         if(msg.length()>8 && msg.substring(0,1).equals("!") && msg.substring(1,msg.indexOf(" ")).equalsIgnoreCase("listUsers")){
            String gr = msg.substring(msg.indexOf(" ") + 1, msg.length());
            //System.out.println("Usuarios no grupo : " + gr);
            Lista.listaDosUsuarios(gr);
            return true;
        }
        // metodo para lista grupos em que o usuario esta
        if(msg.length()>9 && msg.substring(0,1).equals("!") && msg.substring(1,msg.indexOf(" ")).equalsIgnoreCase("listGroups")){
            String grt = msg.substring(msg.indexOf(" ") + 1, msg.length());
            //System.out.println("Grupos : " + grt);
            Lista.listaDoGrupo(grt);
            return true;
        }
        
        return false;
    }
    
    // metodo responsável por enviar a mensagem 
    public static void send(String msg) throws Exception, IOException, TimeoutException{
        if(destinatario != null && !msg.equals("") && msg != null){
			// tratamento para ver a quem  enviar grupo ou usuario
        	if(privado == true){
        		Enviar t = new Enviar(user, destinatario, msg);
	            t.run();
	        }else{
	            Grupo.sendToGrupo(destinatario, user, msg);
	        }
        }
    }
    
    
    public static void main(String[] argv) throws Exception {
        
    	Scanner sc = new Scanner(System.in);
        System.out.print("User: ");
        user = sc.nextLine();
        
        Receber n = new Receber(user);
        user1 = user;
        //esculta msg
        n.start();
        String msg;
        pri = privado;
        
        //loop
        while(true){
        	if(destinatario == null){
        		System.out.print(">> ");
        	} else{
        		System.out.print(destinatario + ">> ");
        	}
        	
        	msg = sc.nextLine();
        	
        	if(!comandos(msg)){
        		send(msg);
        	}
        }
        
    }
}