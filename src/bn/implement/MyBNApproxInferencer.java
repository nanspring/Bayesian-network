package bn.implement;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.RandomVariable;
import bn.parser.XMLBIFParser;

public class MyBNApproxInferencer {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		//MyBNApproxInferencer 10000 aima-alarm.xml B J true M true
		if(args[1].contains(".xml")){
			XMLBIFParser parser=new XMLBIFParser();
			BayesianNetwork bn=parser.readNetworkFromFile(args[1]);
			Assignment asgm=new Assignment();
			for(int i=3;i<args.length;i=i+2){
				RandomVariable r=bn.getVariableByName(args[i]);
				asgm.put(r,args[i+1]);
			}
			bn.getVariableListTopologicallySorted();
			RandomVariable query=bn.getVariableByName(args[2]);
			RejectionSampling rejection=new RejectionSampling();
			Distribution output=new Distribution();
			for(Object x:query.getDomain()){
				output.put(x, 0);
			}
			long startTime = System.currentTimeMillis();
			int count=Integer.valueOf(args[0]);
			System.out.println(rejection.rejection(query, bn, asgm, output, count).toString());
			long endtime = System.currentTimeMillis();
			System.out.println("Runtime:" + (endtime-startTime) + "ms");
		}

	}

}
