package br.ufs.dcomp.ChatRabbitMQ;
import java.net.URL;
import java.io.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;
//import org.json.simple.JSONObject;

// factory.setUri("amqp://franciscols:01020304@rabbit5672-f516192f9315a607.elb.us-east-1.amazonaws.com");
	/*factory.setHost("ip-da-instancia-da-aws"); // Alterar
		factory.setUsername("usuário-do-rabbitmq-server"); // Alterar
		factory.setPassword("senha-do-rabbitmq-server"); // Alterar
		factory.setVirtualHost("/");*/
public class Grupo {
	
	private String nome;
    private String emissor;
    private String msg;
	
	
  public static void sendToGrupo(String grupo, String emissor, String msg) throws Exception, IOException, TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
    	
    		//factory.setUri("amqp://franciscols:01020304@ec2-18-208-214-33.compute-1.amazonaws.com");
        //factory.setUri("amqp://franciscols:01020304@rab5672-b3b2136006aa1bd6.elb.us-east-1.amazonaws.com");
        
        factory.setUri("amqp://simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
            //factory.setUri("amqp://simonerss:123456@ec2-3-84-129-4.compute-1.amazonaws.com");
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Date d = new Date();
        DateFormat hora = new SimpleDateFormat("HH:mm");
        DateFormat dia = new SimpleDateFormat("dd/mm/yyyy");
        String sdata = dia.format(d);
        String shora = hora.format(d);
        String aux="";
		
		//aux = ("(" + dt +")" + emissor+ "#" + grupo + " diz: " + msg);
		
		MensagemProto.Conteudo.Builder conteudo = MensagemProto.Conteudo.newBuilder();
		MensagemProto.Mensagem.Builder mensagem = MensagemProto.Mensagem.newBuilder();
        //conteudo.setTipo(tipo);
        //String snome  = new String(nome, "UTF-8"); // ANTES: conteudo.setNome(nome); // em Bytes
        //conteudo.setNome(snome);
        conteudo.setCorpo(ByteString.copyFromUtf8(msg)); // ANTES: conteudo.setCorpo(msg);
        mensagem.setEmissor(emissor); 
        mensagem.setData(sdata); 
        mensagem.setHora(shora); 
        //mensagem.setGrupo(grupo); 
        mensagem.setConteudo(conteudo);
        
        // obtendo a mensagem
        MensagemProto.Mensagem msgg = mensagem.build(); // ContatoProto.Mensagem msg = mensagem.build();
        // Serializando a mensagem 
        byte[] buffer = msgg.toByteArray();
		
        channel.basicPublish(grupo,"", null, buffer); 
        channel.close();
        connection.close();
  }
  public static void createGrupo(String grupo) throws Exception, IOException, TimeoutException{
			ConnectionFactory factory = new ConnectionFactory();
			
			//factory.setUri("amqp://franciscols:01020304@ec2-18-208-214-33.compute-1.amazonaws.com");
          //  factory.setUri("amqp://franciscols:01020304@rab5672-b3b2136006aa1bd6.elb.us-east-1.amazonaws.com");
            
            factory.setUri("amqp://simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
            //factory.setUri("amqp://simonerss:123456@ec2-3-84-129-4.compute-1.amazonaws.com");
			
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(grupo, "fanout");
            channel.close();
            connection.close();
  }
  
  public static void addUser(String membro, String grupo) throws Exception, IOException, TimeoutException{
	
	    //factory.setUri("amqp://franciscols:01020304@ec2-18-208-214-33.compute-1.amazonaws.com");
	    
	   // factory.setUri("amqp://simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
            //factory.setUri("amqp://simonerss:123456@ec2-3-84-129-4.compute-1.amazonaws.com");
        
        ConnectionFactory factory = new ConnectionFactory();
        //factory.setUri("amqp://franciscols:01020304@rab5672-b3b2136006aa1bd6.elb.us-east-1.amazonaws.com");
        factory.setUri("amqp://simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueBind(membro, grupo, "");
      
  }
  public static void deleteGrupo(String grupo) throws Exception, IOException, TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
		
	    //factory.setUri("amqp://franciscols:01020304@ec2-18-208-214-33.compute-1.amazonaws.com");
	    
        
       // factory.setUri("amqp://franciscols:01020304@rab5672-b3b2136006aa1bd6.elb.us-east-1.amazonaws.com");
        
        
            factory.setUri("amqp://simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
            //factory.setUri("amqp://simonerss:123456@ec2-3-84-129-4.compute-1.amazonaws.com");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDelete(grupo);
        channel.close();
      
  }
  
    public static void DeleteFromGrupo(String user, String grupo) throws Exception, IOException, TimeoutException{
			ConnectionFactory factory = new ConnectionFactory();
		
		    //factory.setUri("amqp://franciscols:01020304@ec2-18-208-214-33.compute-1.amazonaws.com");
           // factory.setUri("amqp://franciscols:01020304@rab5672-b3b2136006aa1bd6.elb.us-east-1.amazonaws.com");
            
            factory.setUri("amqp://simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
            //factory.setUri("amqp://simonerss:123456@ec2-3-84-129-4.compute-1.amazonaws.com");
		  
		  Connection connection = factory.newConnection();
		  Channel channel = connection.createChannel();
		  
		  channel.queueUnbind(user, grupo, "");
		  
		  channel.close();
		  connection.close();
  }
  
  public static void removeUser(String grupo, String membro) throws Exception, IOException, TimeoutException{
      ConnectionFactory factory = new ConnectionFactory();
	
		//factory.setUri("amqp://franciscols:01020304@ec2-18-208-214-33.compute-1.amazonaws.com");
        // factory.setUri("amqp://franciscols:01020304@rab5672-b3b2136006aa1bd6.elb.us-east-1.amazonaws.com");
    
        factory.setUri("amqp://simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
            //factory.setUri("amqp://simonerss:123456@ec2-3-84-129-4.compute-1.amazonaws.com");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueUnbind(membro, grupo, "");
        channel.close();
      
  }
  
  
  
  
  
  
// para enviar arquivos
   private static String lerUrldoArquivo(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 
    
            return buffer.toString();
        } 
        
        catch (IOException e) {}
        finally {
            if (reader != null)
                reader.close();
        }
        return "";
    }
/*
public static void uploadFiles(String caminho) throws Exception, IOException, TimeoutException{
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://franciscols:01020304@rab5672-b3b2136006aa1bd6.elb.us-east-1.amazonaws.com");
    Connection connection = factory.newConnection();
    
    Channel channelfiles = connection.createChannel();
    String falsaa = destinatario+"file";
    channelfiles.queueDeclare(falsaa, false, false, false, null);
    
    
    File origem = new File(caminho);
    FileInputStream fis = new FileInputStream(origem);
    FileChannel inChannel = fis.getChannel();
    
    byte[] buffer = origem.toByteArray();
    
    //RECEBER:
    //writeByteArrayToFile(file, data, false);
  
}
*/
  
}
