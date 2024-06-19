package Tetris;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LeftPanel extends JPanel {
    public Control control;

    //Hold Tetromino 그림.
    protected JPanel HoldPanel = new JPanel();
    protected JLabel HoldLabel = new JLabel("Hold");
    protected JPanel HoldTmino = new JPanel();

    //level 표현을 위한 Panel.
    protected JPanel levelPanel = new JPanel();



    protected JLabel levelLabel = new JLabel("0");

    //시작 시, Hold에 아무것도 없기 때문에.
    boolean blank = true;

    public void setLevelLabel() {
        levelLabel.setText(Integer.toString(control.level));
    }


    public void setLabelFont(JLabel l) {
        l.setFont(new Font("Serif", Font.BOLD, 15));
        l.setHorizontalAlignment(JLabel.CENTER);
    }

    /*
        기존 계획 : GamePanel에서와 같이 Graphics를 사용하여 RightPanel의 다음 블록과 LeftPanel의 Hold 블록을 표현하려고 했음.

        -> 사진으로 저장하는 것이 코딩에 있어 효율적이라고 판단함. 계획을 수정.
        - tetromino라는 폴더에 각각 블록을 캡처한 사진을 저장. 각각의 사진을 control의 bag index에 맞춰 불러오기.
     */
    public void paintHoldTmino(Control control) {
        ImageIcon imageIcon;
        if (blank) {
            HoldTmino.removeAll();
            imageIcon = new ImageIcon("tetromino/Black.png");

        }
        else {
            int tmino = control.holdBox;
            HoldTmino.removeAll();
            imageIcon = new ImageIcon("tetromino/" + tmino + ".png");
        }
        Image image = imageIcon.getImage();
        Image modifiedImage = image.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(modifiedImage);
        JLabel holdPiece = new JLabel(imageIcon);
        HoldTmino.add(holdPiece);
        HoldTmino.revalidate();

    }

    public LeftPanel(Control control) {
        this.control = control;
        this.setLayout(new BorderLayout(0,10));
        setLabelFont(HoldLabel);

        HoldPanel.setLayout(new GridLayout(2,1));
        HoldPanel.setBorder(new EmptyBorder(5,10,5,10));
        //HoldPanel.setBorder(new LineBorder(Color.BLACK));
        levelPanel.setLayout(new GridLayout(2, 1));
        levelPanel.setBorder(new LineBorder(Color.BLACK));

        this.add(HoldPanel,BorderLayout.NORTH);
        HoldPanel.add(HoldLabel, BorderLayout.NORTH);
        HoldPanel.add(HoldTmino, BorderLayout.CENTER);
        paintHoldTmino(control);

        this.add(levelPanel, BorderLayout.SOUTH);
        setLevelLabel();
        levelPanel.add(new JLabel("Level"));
        levelPanel.add(levelLabel);

        setLabelFont(levelLabel);
    }

}
