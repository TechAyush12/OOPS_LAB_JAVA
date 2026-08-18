package demo;

interface lang1 {
	 void python();
}
interface lang2 {
	 void java();
}
class Programlang implements lang1 ,lang2 {
	public void python() {
		System.out.println("The python is easy language as its esay to learn ,understand and implement");
	}
	public void java() {
		System.out.println("Java object oriented language , secure , platform independent ");
	}	
}
public class interface {
	public static void main(String[] args) {
		Programlang p = new Programlang();
		p.python();
		p.java();
	}
}
