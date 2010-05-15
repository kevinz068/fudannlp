package edu.fudan.nlp.tc;
import java.util.ArrayList;
import edu.fudan.ml.types.Alphabet;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.InstanceSet;
import edu.fudan.ml.types.SparseVector;
import edu.fudan.ml.types.Tree;
public class Mean {
	public static SparseVector[] mean (InstanceSet trainingList,Tree tree)
	{
		Alphabet alphabet = trainingList.getLabelAlphabet();
		int numLabels = alphabet.size();
		SparseVector[] means = new  SparseVector[numLabels];
		int[] classNum = new int[numLabels];
		for(int i=0;i<numLabels;i++){
			means[i]=new SparseVector();
		}
		for (int ii = 0; ii < trainingList.size(); ii++){
			Instance inst = trainingList.getInstance(ii);
			SparseVector fv = (SparseVector) inst.getData ();
			int target = (Integer) inst.getTarget();
			if(tree!=null){
				ArrayList<Integer> anc = tree.getPath(target);
				for(int j=0;j<anc.size();j++){
					means[anc.get(j)].add(fv);
					classNum[anc.get(j)]+=1;
				}
			}else{
				means[target].add(fv);
				classNum[target]+=1;
			}
		}
		for(int i=0;i<numLabels;i++){
			means[i].scalarDivide(classNum[i]);
		}
		return means;
	}
}
