package org.example.gamearkanoid.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.example.gamearkanoid.model.BlockBrick;
import org.example.gamearkanoid.model.Brick;

import java.util.*;

public class BlockView {
    private List<Brick> block;
    private BlockBrick blockBrick;
    private static Map<Integer, Image> brickImages = new HashMap<>();;
    private List<ImageView> viewList;
    private List<BrickView> brickViewList;


    public BlockView(BlockBrick blockBrick) {
        this.blockBrick = blockBrick;
        block = this.blockBrick.getBlock();
        viewList = new ArrayList<>();
        brickViewList = new ArrayList<>();
        // cải tiến để chỉ phải gọi một lần trong cả game
        loadBrickImages();
        loadViewList();
    }

    private void loadViewList() {
        for (Brick brick: block) {
            BrickView brickView = new BrickView(brick, getImageForType(brick.getType()));
            viewList.add(brickView.getImageView());
            brickViewList.add(brickView);
        }
    }

    public static Image getImageForType(int type) {
        return brickImages.get(type);
    }

    public void loadBrickImages() {
        try {
            // --- GẠCH THƯỜNG (THEO MAP) ---
            // Gạch 1 máu
            Image brick1 = loadImage("/images/Brick/brick_1_health.png", Brick.WIDTH, Brick.HEIGHT);
            brickImages.put(1, brick1);

            // Gạch 2 máu
            Image brick2 = loadImage("/images/Brick/brick_2_health.png", Brick.WIDTH, Brick.HEIGHT);
            brickImages.put(2, brick2);

//            // Gạch 3 máu
//            Image brick3 = loadImage("/images/brick_3.png"), Brick.WIDTH, Brick.HEIGHT);
//            brickImages.put(3, brick3);

            // Gạch không thể phá hủy
            Image brick9 = loadImage("/images/Brick/brick_indestructible.png", Brick.WIDTH, Brick.HEIGHT);
            brickImages.put(9, brick9);

            // --- CÁC TRẠNG THÁI NỨT --- (số máu ban đầu - 0 - số máu còn lại)
            // Gạch 2 máu bị nứt (còn 1 máu)
            Image brick201 = loadImage("/images/Brick/brick_2_health_cracked.png", Brick.WIDTH, Brick.HEIGHT);
            brickImages.put(201, brick201);

//            // Gạch 3 máu bị nứt 1 (còn 2 máu)
//            Image brick302 = loadImage("/images/brick_3_cracked_1.png"), Brick.WIDTH, Brick.HEIGHT);
//            brickImages.put(302, brick302);
//
//            // Gạch 3 máu bị nứt 2 (còn 1 máu)
//            Image brick301 = loadImage("/images/brick_3_cracked_2.png"), Brick.WIDTH, Brick.HEIGHT);
//            brickImages.put(301, brick301);

        } catch (Exception e) {
            System.err.println("Error loading brick images:");
            e.printStackTrace();
        }
    }

    protected Image loadImage(String path, double width, double height) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)), width, height,true, false);
    }

    public List<ImageView> getViewList() {
        return viewList;
    }
    public void update(Pane pane) {
        // Phải dùng Iterator để có thể xoá phần tử một cách an toàn khi đang duyệt
        Iterator<BrickView> iterator = brickViewList.iterator();

        while (iterator.hasNext()) {
            BrickView brickView = iterator.next();

            // 1. Lấy tham chiếu đến ImageView TRƯỚC KHI update
            //    (Phòng trường hợp .update() làm nó bị null)
            ImageView imageViewToRemove = brickView.getImageView();

            // 2. Gọi update
            brickView.update();

            // 3. Bây giờ kiểm tra xem brick đã bị phá huỷ chưa
            //    (Giả sử dấu hiệu là getImageView() trả về null)
            if (!brickView.getModel().isAlive()) {
                pane.getChildren().remove(brickView.getImageView());
                // 4. Xoá ImageView cũ (giờ nằm trong 'imageViewToRemove')
                //    khỏi danh sách viewList.
                //    (Kiểm tra null để đảm bảo an toàn)
                viewList.remove(brickView.getImageView());

                // 5. Dùng iterator.remove() để xoá brickView khỏi brickViewList
                //    Đây là cách duy nhất an toàn để xoá khi đang duyệt.
                iterator.remove();
                break;
            }
        }
    }
}
