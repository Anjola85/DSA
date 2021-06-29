import java.util.List;

public class Demo {

	public static void main(String[] args) {
		List <String>list = new MidLinkedList<>();
		list.add("0");
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");

		System.out.println(list);
		list.add(0, "-1");
		System.out.println(list);
		list.add(0, "-2");
		System.out.println(list);
		list.add(0, "-3");
		System.out.println(list);
		list.add(0, "-4");
		System.out.println(list);
		list.remove(3);
		System.out.println(list);
		list.remove(1);
		System.out.println(list);
		list.remove(0);
		System.out.println(list);
		list.remove(0);
		System.out.println(list);
		
		list.add(1, "11");
		System.out.println(list);
	}

}
