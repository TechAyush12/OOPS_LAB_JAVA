package inheritance;

class employee {
	void display(String name , int age) {
		System.out.println("Name :- " + name);
		System.out.println("age :- "+ age);
		
	}
}
class personal_info extends employee {
	void info(long mob , int Id) {
		System.out.println("Mobile Number :- " + mob);
		System.out.println("Your Id :- "+ Id);
	}
}

public class inheritance {
	public static void main(String[]args) {
	personal_info p = new personal_info();
	p.display("Ayush Lavhare",18);
	p.info(7897724421L, 2136);
}}