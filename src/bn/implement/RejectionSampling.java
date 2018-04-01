package bn.implement;
import java.util.ArrayList;
import java.util.Random;

import bn.core.*;
public class RejectionSampling {
	public Assignment priorSampling(BayesianNetwork bn){
		ArrayList<RandomVariable> var=(ArrayList<RandomVariable>) bn.getVariableList();
		Assignment output=new Assignment();
		for(int i=0;i<var.size();i++){
			RandomVariable current_rv=var.get(i);
			output.set(current_rv, current_rv.getDomain().get(0));
			double random_prob=new Random().nextDouble();
			double prob=bn.getProb(current_rv, output);
			if(random_prob<=prob){
				output.set(current_rv, current_rv.getDomain().get(0));
			}else{
				output.set(current_rv, current_rv.getDomain().get(1));
			}
		}
		
		return output;
	}
	
	
	public Distribution rejection(RandomVariable X,BayesianNetwork bn,Assignment e,Distribution output,int count){
		for(int i=0;i<count;i++){
			Assignment sample=priorSampling(bn);
			if(isConsistent(sample,e)){
				double current_count=output.get(sample.get(X))+1;
				output.put(sample.get(X), current_count);				
			}
		}
		output.normalize();
		return output;
	}
	
	
	public boolean isConsistent(Assignment sample,Assignment e){
		for(RandomVariable r:e.keySet()){
			if(!e.get(r).equals(sample.get(r))){
				return false;
			}
		}
		return true;
	}

}
