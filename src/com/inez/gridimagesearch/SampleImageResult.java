package com.inez.gridimagesearch;

import java.util.ArrayList;

public class SampleImageResult {

	public static ArrayList<ImageResult> generateSampleImageResult() {
		ArrayList<ImageResult> data = new ArrayList<ImageResult>();
		for ( int i = 0; i < 5; i++ ) {
			data.add(new ImageResult("http://cutestlife.com/wp-content/uploads/2011/03/fluffy_white_dog1.jpg", 600, 338, "http://t3.gstatic.com/images?q=tbn:ANd9GcT31vplvObQ25j3YmLWWRbFMeW2nytXFhGp-U3Op6R8fUuBSJDqh6vfgDs", 135, 76));
			data.add(new ImageResult("http://www.lovethispic.com/uploaded_images/8925-Cute-Fluffy-Dog.jpg", 1280, 853, "http://t3.gstatic.com/images?q=tbn:ANd9GcS39fm9HSVdk832UpMj8VuiiZvy15gkcPLj67Y5HXYOLkRiRKB1jfEjgGM", 150, 100));
			data.add(new ImageResult("http://zwallpaper.biz/hinhanh/anhto/131213White-fluffy-cute-dog-wallpaper.jpg", 2560, 1532, "http://t1.gstatic.com/images?q=tbn:ANd9GcSxM6JlWUoDDMO1mDugIJuXeKVlqpMx8aQntyPUwy6zrugHP0Q9im3eRbI", 150, 90));
			data.add(new ImageResult("http://pinkbluelovescute.com/wp-content/uploads/2012/09/Cutest-little-white-fluffy-puppy.jpeg", 632, 890, "http://t0.gstatic.com/images?q=tbn:ANd9GcQU3YTCt2HV1IuL0Me08OZnFiTAm4xRd8TPBlr8VId8uUQuf_ZtQxn0OjoZ", 104, 146));
			data.add(new ImageResult("http://www.fantom-xp.com/wallpapers/20/Fluffy_Kitten_Desktop.jpgB", 1024, 768, "http://t0.gstatic.com/images?q=tbn:ANd9GcSz0pMiogNPGl3j6xnnaKjocCSp-YnGcNQNSNSv_SW1rxLDORcqw85NMr9B", 150, 113));
			data.add(new ImageResult("http://cdn3.www.babble.com/wp-content/uploads/2013/07/13-7.jpg", 1200, 1600, "http://t1.gstatic.com/images?q=tbn:ANd9GcR37-JyVaApCeVDB02MbtjGJlFIxtAmzWpK2xPLzPHInl4-qCzboMbrAAX9", 113, 150));
		}
		return data;
	}
	
}
