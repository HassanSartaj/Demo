/**
 * 
 */
package dsml.tree.reader.main;

import dsml.tree.Node;
import dsml.tree.Tree;

/**
 * @author Hassan Sartaj
 * @version 1.0
 */
public class TreeReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ModelLoader loader = new ModelLoader();
		Object model=loader.loadModel("models/My.tree");
		
		if(model instanceof Tree)
		{
			Tree tree= (Tree) model;
			Node root=tree.getNode();
			printTreeNodes(root);
		}
	}

	private static void printTreeNodes(Node root) {
		System.out.println("Node: "+root.getLabel()+" Data: "+root.getData());
		for(Node n: root.getChildren()) {
			if(n.getChildren().isEmpty()) {
				System.out.println("Node: "+n.getLabel()+" Data: "+n.getData());
			}
			else {
				System.out.println("Node: "+n.getLabel()+" Data: "+n.getData());
				for(Node c: n.getChildren()) {
					printTreeNodes(c);
				}
			}
		}
	}

}
