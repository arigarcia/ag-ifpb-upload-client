package ag.upload;

import it.sauronsoftware.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;


public class Main {
  
  public static void main(String[] args) throws JAXBException, IOException {
    //arquivo
    File file = new File("rep/image-1.png");
    //verificar se o arquivo existe
    if (file.exists()){
      //
      long l = file.length();
      //
      byte[] b = new byte[(int)l];
      //
      FileInputStream in = new FileInputStream(file);
      in.read(b);
      in.close();
      //
      byte[] c = Base64.encode(b);
      //
      String imgstring = new String(c);
      //
      AGImage img = new AGImage();
      img.setImage(imgstring);
      //
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      //
      JAXBContext ctx = JAXBContext.newInstance(AGImage.class);
      Marshaller marshaller = ctx.createMarshaller();
      marshaller.marshal(img, out);
      //
      String text = new String(out.toByteArray());
      //
      StringRepresentation entity = new StringRepresentation(
          text, MediaType.APPLICATION_XML
      );
      //
      ClientResource client = new ClientResource("http://10.1.1.106:9191/xml/upload");
      StringRepresentation r = (StringRepresentation) client.post(entity);
      System.out.println(r.getText());
    }
  }

}
