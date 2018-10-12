package test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class Report {
	@XmlElement
	private String title;
	@XmlElement
	private String author;
	@XmlElement
	private int year;
	@XmlElementWrapper(name = "factorList")
	@XmlElement(name = "factor")
	private List<Factor> factorList;

	public Report() {
		title = "这是报告的 title";
		author = "obama";
		year = 2012;

		factorList = new ArrayList<Factor>();
		factorList.add(new Factor());
		factorList.add(new Factor());
		factorList.add(new Factor());
	}
}

class Factor {

	@XmlElement
	String name;
	@XmlElement
	String decision;
	@XmlElement
	int value;

	public Factor() {
		name = "factor test";
		decision = "high";
		value = 100;
	}
}

public class testJAXB {

	public static void main(String[] args) throws JAXBException, IOException {

		JAXBContext context = JAXBContext.newInstance(Report.class);

		// 从JAVA到XML
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(new Report(), System.out);

		FileWriter writer = new FileWriter("f:/report.xml");
		marshaller.marshal(new Report(), writer);

		
		
		
		// 从XML到JAVA
		FileReader reader = new FileReader("f:/report.xml");

		Unmarshaller unmarshaller = context.createUnmarshaller();
		Report report = (Report) unmarshaller.unmarshal(reader);

		System.out.println();
		System.out.println("ok");

	}
}