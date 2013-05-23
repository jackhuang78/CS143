class TypeTree {
	
	private Node root;

	public TypeTree() {
		root.value = AbstractTable.idTable.lookup("Object");
	}


	private static class Node {
		public AbstractSymbol value;
		public List<Node> childrens;
	}
}
