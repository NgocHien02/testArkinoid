package org.example.gamearkanoid.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.gamearkanoid.model.Brick;

import java.util.HashMap;
import java.util.Map;

public class BrickView extends BaseView<Brick>{

    Image image;
    public BrickView(Brick brick, Image image) {
        super(brick);
        this.image = image;
        setImageView(new ImageView(image));
        initial();
    }
    @Override
    public void update() {
        if (model.getInitialHealth() == 2 && model.getHealth() == 1) {
            image = BlockView.getImageForType(201);
        }
//        // Nếu là gạch 3 máu ban đầu
//        if (initialHealth == 3) {
//            if (health == 2) {
//                newImage = blockBrickManager.getImageForType(302); // Lấy ảnh "3_cracked_1"
//            } else if (health == 1) {
//                newImage = blockBrickManager.getImageForType(301); // Lấy ảnh "3_cracked_2"
//            }
//        }
        this.imageView.setImage(image);
    }
}
