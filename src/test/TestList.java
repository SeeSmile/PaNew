package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Myc> list = new ArrayList<>();
		list.add(new Myc(1, "fupei1"));
		list.add(new Myc(8, "fupei1"));
		list.add(new Myc(5, "fupei1"));
		list.add(new Myc(7, "fupei1"));
		list.add(new Myc(2, "fupei1"));
		System.out.println(list.toString());
		Collections.sort(list, new Comparator<Myc>() {

			@Override
			public int compare(Myc o1, Myc o2) {
				if(o1.getAge() > o2.getAge()) {
					return -1;
				}
				return 1;
			}
		});
		System.out.println(list.toString());
	}
	
	public static class Myc{
		private int age;
		private String name;
		public Myc(int age, String name) {
			this.age = age;
			this.name = name;
		}
		
		
		public int getAge() {
			return age;
		}


		public void setAge(int age) {
			this.age = age;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		@Override
		public String toString() {
			return "age:" + age + ", name:" + name + "\n";
		}
 	}

}
