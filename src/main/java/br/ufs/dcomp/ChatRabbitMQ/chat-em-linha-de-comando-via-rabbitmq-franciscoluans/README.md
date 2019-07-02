# ChatRabbitMQ

https://docs.microsoft.com/pt-br/azure/service-bus-messaging/service-bus-java-how-to-use-jms-api-amqp
https://stackoverflow.com/questions/31648066/how-to-send-and-receive-file-with-java-rabbitmq
https://king.host/blog/2018/01/como-gerenciar-filas-php-com-rabbitmq/









Esta tarefa consiste no desenvolvimento de um cliente de chat do tipo linha de comando usando o RabbitMQ como servidor de mensagens de acordo com o apresentado em sala de aula.

Ver demais arquivos das etapas do projeto.
# ExemploRabbitMQ

Como gerar jar usando maven:

mvn clean compile assembly:single

java -jar target/ Receptor.jar  


java read whole file


# prováveis funções para serem usadas no projeto 
função para remover caracteres
String old = "testando remoção de caracteres em uma string teste";
String nova = teste.replace("test", "");
ou
String old = "testando remoção de caracteres em uma string teste";
String nova = teste.delete("test", "");

verificar caracteres

public void verificaNome()
	{
	    String aux = nomediscip;
	     if(aux.length() > 15)
	      {
	          System.out.println("Nome da disciplina excede o limite de 15 caracteres");
	       }
            for(int i=0; i < aux.length(); i++)
            {
                if(aux.charAt(i) == '!')
	         System.out.println("erro de caracter");
	   }
      }
      
      
      String s = "Java";
for(int i = 0; i < s.length(); i++)
{
    if(s.charAt(i) == 'a')
        System.out.printnl("s[" + i + "] = a" );
}


String a = "aaa";
if ( a.equals( String.valueOf( '#' ) ) {
    // a é igual a #
}