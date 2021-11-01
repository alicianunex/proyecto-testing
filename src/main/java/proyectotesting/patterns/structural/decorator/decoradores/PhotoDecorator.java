package proyectotesting.patterns.structural.decorator.decoradores;


import proyectotesting.patterns.structural.decorator.Photo;

public abstract class PhotoDecorator extends Photo {
	
	protected Photo photo;

	protected PhotoDecorator(Photo photo) {
		super();
		this.photo = photo;
	}
	
	
	

}
