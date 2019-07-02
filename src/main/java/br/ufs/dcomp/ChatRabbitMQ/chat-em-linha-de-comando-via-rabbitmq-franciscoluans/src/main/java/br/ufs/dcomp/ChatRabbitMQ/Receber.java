package br.ufs.dcomp.ChatRabbitMQ;

import com.rabbitmq.client.*;
import java.io.IOException;
//import java.io.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;


public class Receber extends Thread {

    private  String usuario;
    private  String mm;
    private  ConnectionFactory factory = null;
    private  String msgpronta;

    
    public Receber(String re){
        this.usuario=re;
    }
    
    public void run(){
        try{
            ConnectionFactory factory = new ConnectionFactory();
            //etapa 4 
            // rabbit802-304f7797e4d394cd.elb.us-east-1.amazonaws.com
            //factory.setUri("amqp://franciscols:01020304@rab5672-b3b2136006aa1bd6.elb.us-east-1.amazonaws.com");
    	    //factory.setUri("amqp://franciscols:01020304@ec2-18-208-214-33.compute-1.amazonaws.com");
    	    factory.setUri("amqp://simonerss:123456@ec2-3-84-129-4.compute-1.amazonaws.com");
            // factory.setUri("amqp:simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
            
            //abre uma conexão
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
    		//declara uma fila para o usuário
            channel.queueDeclare(usuario, false, false, false, null);
            
            //declara um consumidor
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(usuario, true, consumer);
    
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String mm = "";
                
                // Mapeando bytes para a mensagem protobuf 
                MensagemProto.Mensagem msg = MensagemProto.Mensagem.parseFrom(delivery.getBody());
                //MensagemProto.Conteudo cont = MensagemProto.Conteudo.parseFrom(delivery.getBody());
                    
                // Extraindo dados da mensagem
                String emissor = msg.getEmissor();
                String data = msg.getData();
                String hora = msg.getHora();
                String grupo = msg.getGrupo();
                
               MensagemProto.Conteudo cop = msg.getConteudo();
               ByteString tx = cop.getCorpo();
               String vx = tx.toString("UTF-8");
                // extrair o conteudo
                //String tipo = cont.getTipo();
               // ByteString corpo = cont.getCorpo();
                 //String vv = corpo.getBody();
                //String vv = corpo.toString("UTF-8");
                 // String msgDecod = new String(corpo,"UTF-8");
                //msgpronta.setCorpo(ByteString.copyFromUtf8(msgDecod));
                //String nome = cont.getNome();
                
                //+ " - " +vv
                mm = ("(" + data +" às "+ hora +") "+ emissor + " diz: " + vx );
                
                //String message = new String(delivery.getBody()); 
               
    			//System.out.println(message);
    			System.out.println(mm);
                if(Mainchat.user1!=null){
                    if(Mainchat.pri==true){
                        System.out.print(Mainchat.user1+">>");
                    }else{
                    System.out.print("\n"+Mainchat.user1+"*>>");
                    }
                }else{
                    System.out.print(">>");
                }
             }
    
             
        }catch(Exception ex){}
    }
    }











// anotações




/*
MensagemProto.Mensagem mg = MensagemProto.Mensagem.parseFrom(delivery.getBody());
// Extraindo dados da mensagem
String emissor_ = mg.getEmissor();
String dt = mg.getData();
String hh = mg.getHora();
String gp = mg.getGrupo();
//m vazio
//String bs = mg.getCorpo();

//byte[] bb = mg.getCorpo();
//ByteString bs = ByteString.copyFrom(bb);

//byte[] buf = bs.toByteArray();
//String l = buf.toString();
//ByteString bs = ByteString.copyFrom(mg.getCorpo());

byte[] msgBytes = ByteString.copyFrom(mg.getCorpo());
//byte[] msgBytes = mg.getConteudo();
String aa = msgBytes.toString();

*INICIA UM COMENTÁRIO DE BLOCO*
MensagemProto.Conteudo cc = MensagemProto.Conteudo.parseFrom(delivery.getBody());

ByteString xx = mg.getConteudo();
String ti = cc.getTipo();
ByteString co = xx.getCorpo();
byte[] co1 = co.toString();
String no = cc.getNome();

//String vv = com.toString("UTF-8");
String msgDecod = new String(co1,"UTF-8");
// String co1 = co.toString();
*FINALIZA COMENTÁRIO DE BLOCO*

*/




/*while (true) {
                /*QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody()); 
                JSONObject obj = (JSONObject) parser.parse(message);
                System.out.println("\n("+obj.get("data")+") "+obj.get("name")+" diz: "+obj.get("msg"));
                
    			Consumer consumer = new DefaultConsumer(channel) {
    			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws Exception, IOException {
    		    String message = new String(body, "UTF-8");
    			  //MensagemProto.Mensagem mg = MensagemProto.Mensagem.parseFrom(body);
    			// Extraindo dados da mensagem
    			//String emissor_ = mg.getEmissor();
    			//String dt = mg.getData();
    			//String hh = mg.getHora();
    			//String gp = mg.getGrupo();
    			
    		   //MensagemProto.Conteudo cc = MensagemProto.Conteudo.parseFrom(body);
    			
    		   //String ti = cc.getTipo();
    			//String co = cc.getCorpo();
    		  // String no = cc.getNome();
    		  
    		   //mm = ("(" + dt +" às "+ hh +") "+ emissor_ + " diz: " + co );
    		  
    		  System.out.println(message);
    		  //System.out.println(mm);
    }
    
    };                //(queue-name, autoAck, consumer);    
    
    		channel.basicConsume(usuario, true, consumer);
    		/*	//poste do cabeçalho
    			if(usuario!=null){
                    if(privado==true){
                        System.out.print(usuario+">>");
                    }else{
                    System.out.print("\n"+usuario+"*>>");
                    }
                }else{
                    System.out.print(">>");
                }
             
             
             }*/  