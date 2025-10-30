package org.example.gamearkanoid.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.gamearkanoid.model.Brick;

import java.util.HashMap;
import java.util.Map;

public class BrickView extends BaseView<Brick>{
    //default image
    private Image image = new Image(getClass().getResourceAsStream("/images/Brick/brick_1_health.png"), Brick.DEFAULT_WIDTH_BRICK, Brick.DEFAULT_HEIGHT_BRICK, true, false);
    public BrickView() {
        super();
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
