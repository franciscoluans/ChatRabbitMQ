package br.ufs.dcomp.ChatRabbitMQ;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;
//import com.rabbitmq.client.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;
import java.util.Scanner;
import java.util.*;
import java.text.*;
import java.time.LocalDateTime;

public class Enviar{
    
    private String emissor;
    private String destinatario;
    private String msg;
    private byte[] nome;
	private String aux;
	private String tipo;
	private String nometipo;

    public Enviar(String emissor,String destinatario, String msg){
        this.emissor=emissor;
        this.destinatario=destinatario;
        this.msg=msg;
    }
    
    public void run(){
        try{
            ConnectionFactory factory = new ConnectionFactory();
            //etapa 4 rabbit802-304f7797e4d394cd.elb.us-east-1.amazonaws.com
           // factory.setUri("amqp://franciscols:01020304@rab5672-b3b2136006aa1bd6.elb.us-east-1.amazonaws.com");
		//	factory.setUri("amqp://franciscols:01020304@ec2-18-208-214-33.compute-1.amazonaws.com");
			// agora seu login está dando erro [AMQP Connection 3.84.129.4:5672] WARN com.rabbitmq.client.impl.ForgivingExceptionHandler - An unexpected connection driver error occured (Exception message: Socket closed)
            //mesmo que o meu 
            
            factory.setUri("amqp://simonerss:123456@rabbitmq5672-afd57342900be0f3.elb.us-east-1.amazonaws.com");
            //factory.setUri("amqp://simonerss:123456@ec2-3-84-129-4.compute-1.amazonaws.com");
            Connection connection = factory.newConnection();
            //canal para msg
            Channel channelmsg = connection.createChannel();
            channelmsg.queueDeclare(destinatario, false, false, false, null);
            //canal para arquivos tem que altera o nome da fila pois está sendo duplicada no canal
            
            
            
            Date d = new Date();
            DateFormat hora = new SimpleDateFormat("HH:mm");
            DateFormat dia = new SimpleDateFormat("dd/mm/yyyy");
            String data = dia.format(d);
            String shora = hora.format(d);
            tipo="";
            nometipo="";
        
	        MensagemProto.Conteudo.Builder conteudo = MensagemProto.Conteudo.newBuilder();
            //conteudo.setTipo(tipo);
            //String snome  = new String(nome, "UTF-8"); // ANTES: conteudo.setNome(nome); // em Bytes
            //conteudo.setNome(snome);
            conteudo.setCorpo(ByteString.copyFromUtf8(msg)); // ANTES: conteudo.setCorpo(msg);
            
            MensagemProto.Mensagem.Builder mensagem = MensagemProto.Mensagem.newBuilder();
            mensagem.setEmissor(emissor); 
            mensagem.setData(data); 
            mensagem.setHora(shora); 
            //mensagem.setGrupo(grupo); 
            mensagem.setConteudo(conteudo);
            
            // obtendo a mensagem
            MensagemProto.Mensagem msgg = mensagem.build(); // ContatoProto.Mensagem msg = mensagem.build();
            // Serializando a mensagem 
            byte[] buffer = msgg.toByteArray();
            
            
            // Escrevendo contato já serializado em arquivo
            FileOutputStream fos = new FileOutputStream(new File("msg.bin"));
            fos.write(buffer);
            fos.close();
            
            //System.out.println("Mensagem escrita em formato binário no arquivo \"mensagem.bin\"");
            
            // Mapeando a mensagem para o formato json
            String json = JsonFormat.printer().print(mensagem);
            
            // Escrita do conteúdo json em arquivo texto
            fos = new FileOutputStream(new File("mensagem.json"));
            fos.write(json.getBytes());
            fos.close();
        
            //System.out.println("Contato escrito em formato texto/json no arquivo \"contato.json\""); 
            
            
           MensagemProto.Mensagem msgsend = mensagem.build();
           byte[] buffer1 = msgsend.toByteArray();
        
    
            //msg_formatada = ("(" + data2 + " às " + hora + ") " + destinatario.substring(1) + " diz: " + msg );
    
    
            channelmsg.basicPublish("",destinatario, null,  buffer1);
            //para teste 
            
             //System.out.println("[x] Mensagem enviada: '" + msg + "'");
            //aux = ("(" + dt +")" + nome + " diz: " + msg);
            
            
			
			//publicar a mensagem 
           // channel.basicPublish("", destinatario, null, msg.getBytes());not use channel
           //channelmsg.basicPublish("",destinatario, null,  buffer);
            
        //   } //fechar o canal
            channelmsg.close();
        
        }catch(Exception ex){
            //System.out.println("Erro"+ex.);
            ex.printStackTrace();
        }
    
    }
    
    //TRASH:
    
    /**
    * FIX: Tu deve converter tudo que é em bytes do ProtoBuffer para
    * ByteString, primeiro transformando uma string para os bytes,
    * deixando esses bytes em utf-8, depois transformando em ByteString
    **/
    
    //conteudo.setNomeBytes( ByteString.copyFrom(nometipo.getBytes("UTF-8") ) );
    
    //msg2.setGrupo();
    /** FIX: Aqui não é para usar "addConteudo" e sim setonteudo, pois não está usando `repeated`
     * no protobuffer, o que é usado para usar em array
     **/
    
}
