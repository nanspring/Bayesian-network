package bn.implement;


import java.util.ArrayList;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.RandomVariable;
import bn.inference.Inferencer;
import java.util.ArrayList;
import java.util.List;

import bn.core.*;

public class EnumerationAsk implements Inferencer {
	@Override
	public Distribution ask(BayesianNetwork bn,RandomVariable query,Assignment evidence){
		Distribution Q= new Distribution();
		for(Object x:query.getDomain()){
			Assignment temp=evidence.copy();
			temp.set(query, x);
			ArrayList<RandomVariable> RV_List=(ArrayList<RandomVariable>) bn.getVariableList();
			double value=enumerate_all(RV_List,temp,bn);
			Q.put(x, value);
		}
		Q.normalize();
		return Q;
		
	}
	public double enumerate_all(ArrayList<RandomVariable> RV_List,Assignment evidence, BayesianNetwork bn){
		ArrayList<RandomVariable> rv=new ArrayList<RandomVariable>();
		rv=(ArrayList<RandomVariable>) RV_List.clone();
		if(RV_List.isEmpty()){
			return 1.0;
		}		
		RandomVariable y=RV_List.get(0);
		rv.remove(0);	
		if(evidence.variableSet().contains(y)){
			Assignment e=evidence.copy();
			System.out.println(e.toString());
			return bn.getProb(y, e)*enumerate_all(rv, e,bn);
			
		}else{	
			double prob=0;
			for(Object value:y.getDomain()){
				Assignment e=evidence.copy();
				e.set(y, value);			
				prob=prob+bn.getProb(y, e)*enumerate_all(rv,e,bn);
			}
			return prob;
		}
		
	}

}

