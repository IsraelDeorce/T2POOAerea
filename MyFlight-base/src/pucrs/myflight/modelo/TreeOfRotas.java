package pucrs.myflight.modelo;

import java.util.ArrayList;
import java.util.LinkedList;

public class TreeOfRotas {	
	
	private Node root;
	private int count;	
	
	public class Node{
		
		public Node father;
		public Aeroporto aeroporto;
		public LinkedList<Node> ligacoes;
		
		public Node(Aeroporto aeroporto){			
			father = null;
			this.aeroporto = aeroporto;
			ligacoes = new LinkedList<Node>();
		}
		
		public void addLigacao(Node n){
			n.father = this;
			ligacoes.add(n);					
		}		
		
		public Node getLigacoes(int i) {
			if(i<0 || i>=ligacoes.size())				
				throw new RuntimeException("Index inválido");
			return ligacoes.get(i);				
		}
		
		public int getSubtreeSize(){
			return ligacoes.size();
		}
		
		public Aeroporto getElement(){
			return aeroporto;
		}
		
	}
	public TreeOfRotas(Aeroporto inicial){
		root = new Node(inicial);
		count++;
	}
	
	//Retorna referencia de um nodo que possua determinado elemento a partir de uma arvore/subarvore
	public Node searchNodeRef(Aeroporto element, Node target){
		Node n = null;
		if(target != null){
			if(element.equals(target.aeroporto))
				n = target;
			else{
				int i=0;
				Node aux = null;
				while(aux==null && i<target.getSubtreeSize()){
					aux = searchNodeRef(element, target.getLigacoes(i));
					i++;
				}
				n=aux;
			}
		}
		return n;	
	}
	
	// insere o elemento e como	filho de father
	public boolean add(Aeroporto element, Aeroporto father){
		Node n = new Node(element);
		if(father==null){//se for nulo, é para adicionar na raiz
			if(root!=null){	
				root.father = n;
				n.addLigacao(root);
			}
			root = n;			
		}
		//se father não é nulo, é para adicionar como subtree dele
		else{
			Node aux = searchNodeRef(father, root);
			if(aux==null)
				return false;
			aux.addLigacao(n);
			n.father = aux;		
		}
		count++;
		return true;		
	}	
		
	//retorna o pai do elemento e
	public Aeroporto getParent(Aeroporto r){
		Node n = searchNodeRef(r, root);
		if(n==null)
			throw new RuntimeException("Elemento não encontrado!");
		return n.father.aeroporto;
	}
			
	//retorna true se a árvore contém o	elemento
	public boolean contains(Aeroporto e){
		if(searchNodeRef(e,root)!=null)
			return true;
		return false;
	}
	
	//retorna o número de elementos armazenados na árvore
	public int size(){
		return count;
	}
	
	//remove todos os elementos da árvore
	public void clear(){
		root = null;
		count = 0;
	}
	
	public Node getRoot(){
		return root;
	}
	
	/*
	//retorna uma lista com todos os elementos da árvore na ordem pré-fixada
	public LinkedListOfString positionsPre(){
		LinkedListOfString lista = new LinkedListOfString();
		positionsPreAux(root, lista);
		return lista;		
	}
	public void positionsPreAux(Node n, LinkedListOfString lista){
		if(n!=null){
			lista.add(n.element);
			for(int i=0; i<n.getSubtreeSize();i++){
				positionsPreAux(n.getSubtree(i),lista);
			}			
		}
	}
	
	//retorna uma lista com todos os elementos da árvore na ordem pos-fixada
	public LinkedListOfString positionsPos(){
		LinkedListOfString lista = new LinkedListOfString();
		positionsPosAux(root, lista);
		return lista;		
	}
	public void positionsPosAux(Node n, LinkedListOfString lista){
		if(n!=null){			
			for(int i=0; i<n.getSubtreeSize();i++){
				positionsPosAux(n.getSubtree(i),lista);
			}
			lista.add(n.element);
		}
	}
	
	// retorna uma lista com todos os elementos da árvore com um caminhamento em largura
	public LinkedList<Rota> positionsWidth(){
		LinkedList<Rota> lista = new LinkedList<Rota>();
		if(root!=null){
			ArrayList<Node> destinos = new ArrayList<Node>();
			Node aux = root;
			destinos.add(aux);
			while(!destinos.isEmpty())
				aux=destinos.remove();
				lista.add(aux.element);
				for(int i=0; i<aux.getSubtreeSize(); i++)
					fila.enqueue(aux.getSubtree(i));			
		}
		return lista;
	}
	
	*/
}
