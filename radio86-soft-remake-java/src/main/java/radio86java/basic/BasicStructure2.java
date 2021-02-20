package radio86java.basic;

import java.util.List;
import java.util.Map;

public class BasicStructure2 {

	List<Expression> expressions;
	Map<Integer, Integer> linenumbers;

	BasicStructure2(List<Expression> expressions, Map<Integer, Integer> linenumbers) {
		this.expressions = expressions;
		this.linenumbers = linenumbers;
	}

	void print() {
		for (int i = 0; i < expressions.size(); i++) {
			Expression e = expressions.get(i);
			e.print();
		}
		for(Integer key : linenumbers.keySet()) {
			System.out.println("line N " + key + " => index " + linenumbers.get(key));
		}
	}

}
