package bn.implement;
import bn.parser.*;
import bn.examples.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bn.core.*;
public class MyBNInferencer {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		if(args[0].contains(".xml")){
			XMLBIFParser parser=new XMLBIFParser();
			BayesianNetwork bn=parser.readNetworkFromFile(args[0]);
			Assignment asgm=new Assignment();
			for(int i=2;i<args.length;i=i+2){
				RandomVariable r=bn.getVariableByName(args[i]);
				asgm.put(r,args[i+1]);
			}
			EnumerationAsk enumeration=new EnumerationAsk();
			System.out.println(enumeration.ask(bn, bn.getVariableByName(args[1]), asgm));


			//second sampling technique
//			bn.getVariableListTopologicallySorted();
//			bn.print();
//			RandomVariable query=bn.getVariableByName(args[1]);
//			RejectionSampling rejection=new RejectionSampling();
//			Distribution output=new Distribution();
//			for(Object x:query.getDomain()){
//				output.put(x, 0);
//			}
//			System.out.println("distribution "+output.toString());
//			//System.out.println(rejection.priorSampling(bn));
//			long startTime = System.currentTimeMillis();
//			System.out.println(rejection.rejection(query, bn, asgm, output, 100000).toString());
//			long endtime = System.currentTimeMillis();
//			System.out.println("Runtime:" + (endtime-startTime) + "ms");
		}else if(args[0].contains(".bif")){
			BIFParser parser=new BIFParser(new FileInputStream(args[0]));
			BayesianNetwork bn=parser.parseNetwork();
			bn.getVariableListTopologicallySorted();
			bn.print();
			Assignment asgm=new Assignment();
			for(int i=2;i<args.length;i=i+2){
				RandomVariable r=bn.getVariableByName(args[i]);
				asgm.put(r,args[i+1]);
			}
			EnumerationAsk enumeration=new EnumerationAsk();
			System.out.println("query "+bn.getVariableByName(args[1]));
			System.out.println(enumeration.ask(bn, bn.getVariableByName(args[1]), asgm));
		}
	}
}
