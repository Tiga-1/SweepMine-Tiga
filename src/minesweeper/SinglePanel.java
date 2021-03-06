package minesweeper;

import components.GridComponent;
import components.SingleComponent;
import entity.GridStatus;
import openWindow.InitialWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SinglePanel extends JPanel {
    private static SingleComponent[][] mineField;
    private static int[][] chessboard;
    private int[][] currentState;//记录格子们的当前打开状态
    private ArrayList<ArrayList<Integer>> saveOfMine;//存档中的雷场
    //用于记录雷区的状态，0代表未点开（covered)，-1代表点开是雷(bombed)，
    // 1代表是雷并正确插旗(flag)，2代表点开了，但是安全数字(clicked),-2代表错误插旗(wrong)
    private final Random random = new Random();
    private static int xCount;
    private static int yCount;
    private int mineCount;

    public static SinglePanel singlePanel;
    private int LeiCode = -1;


    /*
     * 初始化一个具有指定行列数格子、并埋放了指定雷数的雷区。
     *
     * @param xCount    count of grid in column
     * @param yCount    count of grid in row
     * @param mineCount mine count
     */
    //注意：xCount代表行数，yCount代表列数
    //直接生成游戏
    public SinglePanel(int xCount, int yCount, int mineCount) {

        singlePanel = this;
        this.xCount = xCount;
        this.yCount = yCount;
        this.mineCount = mineCount;

        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.setSize(SingleComponent.gridSize * yCount, SingleComponent.gridSize * xCount);

        initialGame1();

        repaint();
    }


    //直接生成雷场和按钮们
    public void initialGame1() {
        mineField = new SingleComponent[xCount][yCount];
        //初始化棋盘
        generateChessBoard();
        //对按钮们进行了初始化
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                SingleComponent gridComponent = new SingleComponent(i, j, chessboard[i][j]);
                gridComponent.setContent(chessboard[i][j]);
                gridComponent.setLocation(j * SingleComponent.gridSize, i * SingleComponent.gridSize);
                mineField[i][j] = gridComponent;
                this.add(mineField[i][j]);
            }
        }
        //对所记录的按钮们的状态进行了初始化，默认是0即未点开
        generateState();
    }


    //产生雷场并将格子们正确地赋值，是雷还是周围的雷数
    public void generateChessBoard() {
        //todo: generate chessboard by your own algorithm
        chessboard = new int[xCount][yCount];//定义棋盘大小
        int count = 1;//从0改为1了，原来生成的雷数会差1
        //埋下指定数量的雷数，现在只写了-1
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                if (count <= mineCount) {
                    chessboard[i][j] = -1;
                    count++;
                }
            }
        }
        resetMine();
        //如果打乱后出现9雷，重新洗牌直至无9雷
        while (checkMine()) {
            resetMine();
        }
        //计算周边雷的数量，初始化其他格子的数字
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                if (chessboard[i][j] == LeiCode) {
                    continue;
                }
                int tempCount = 0;
                if (i > 0 && j > 0 && chessboard[i - 1][j - 1] == LeiCode) {
                    tempCount++;
                }
                if (i > 0 && chessboard[i - 1][j] == LeiCode) {
                    tempCount++;
                }
                if (i > 0 && j < yCount - 1 && chessboard[i - 1][j + 1] == LeiCode) {
                    tempCount++;
                }
                if (j > 0 && chessboard[i][j - 1] == LeiCode) {
                    tempCount++;
                }
                if (j < yCount - 1 && chessboard[i][j + 1] == LeiCode) {
                    tempCount++;
                }
                if (i < xCount - 1 && j > 0 && chessboard[i + 1][j - 1] == LeiCode) {
                    tempCount++;
                }
                if (i < xCount - 1 && chessboard[i + 1][j] == LeiCode) {
                    tempCount++;
                }
                if (i < xCount - 1 && j < yCount - 1 && chessboard[i + 1][j + 1] == LeiCode) {
                    tempCount++;
                }
                chessboard[i][j] = tempCount;
            }
        }
    }


    //检查雷的分布，找9个雷的情况
    public boolean checkMine() {
        boolean check = false;
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                int count = 0;
                if (chessboard[i][j] == -1) {
                    count++;
                }
                if (j - 1 >= 0 && chessboard[i][j - 1] == -1) {
                    count++;
                }
                if (j + 1 < yCount && chessboard[i][j + 1] == -1) {
                    count++;
                }
                if (i - 1 >= 0 && chessboard[i - 1][j] == -1) {
                    count++;
                }
                if (i + 1 < xCount && chessboard[i + 1][j] == -1) {
                    count++;
                }
                if (i - 1 >= 0 && j - 1 >= 0 && chessboard[i - 1][j - 1] == -1) {
                    count++;
                }
                if (i - 1 >= 0 && j + 1 < yCount && chessboard[i - 1][j + 1] == -1) {
                    count++;
                }
                if (i + 1 < xCount && j - 1 >= 0 && chessboard[i + 1][j - 1] == -1) {
                    count++;
                }
                if (i + 1 < xCount && j + 1 < yCount && chessboard[i + 1][j + 1] == -1) {
                    count++;
                }
                if (count == 9) {//有9雷是true；
//                    check = true;
//                    break;
//                }
//            }
//            if (check) {
//                break;
//            }
//        }
//        return check;//没有9雷false；
//        // 暂时还没有满足用户第一次点到雷的情况
//    }
                    return true;
                }
            }
        }
        return false;
    }

    //洗雷，使得雷重新分布
    public void resetMine() {
        //先洗一次牌再说
        //对每一行重新洗牌 i在外层，j在内层
        for (int i = xCount - 1; i >= 0; i--) {
            for (int j = yCount - 1; j > 0; j--) {
                int a = random.nextInt(j);//小心数组越界！！！
                int rem = chessboard[i][a];
                chessboard[i][a] = chessboard[i][j];//得记住之前的数啊，是交换！！！
                chessboard[i][j] = rem;
            }
        }
        //对每一列重新洗牌，i在内，j在外
        for (int j = yCount - 1; j >= 0; j--) {
            for (int i = xCount - 1; i > 0; i--) {
                int b = random.nextInt(i);
                int rem = chessboard[b][j];
                chessboard[b][j] = chessboard[i][j];
                chessboard[i][j] = rem;
            }
        }
        while (this.checkMine()) {//有9雷
            //以下用洗牌算法防止雷区过度密集
            //对每一行重新洗牌 i在外层，j在内层
            for (int i = xCount - 1; i >= 0; i--) {
                for (int j = yCount - 1; j > 0; j--) {
                    int a = random.nextInt(j);//小心数组越界！！！
                    int rem = chessboard[i][a];
                    chessboard[i][a] = chessboard[i][j];//得记住之前的数啊，是交换！！！
                    chessboard[i][j] = rem;
                }
            }
            //对每一列重新洗牌，i在内，j在外
            for (int j = yCount - 1; j >= 0; j--) {
                for (int i = xCount - 1; i > 0; i--) {
                    int b = random.nextInt(i);
                    int rem = chessboard[b][j];
                    chessboard[b][j] = chessboard[i][j];
                    chessboard[i][j] = rem;
                }
            }

        }
    }

    //初始化记录格子打开状态的数组为0，即未打开
    public void generateState() {
        currentState = new int[xCount][yCount];
        for (int m = 0; m < xCount; m++) {
            for (int n = 0; n < yCount; n++) {
                currentState[m][n] = 0;
            }
        }
    }

    /**
     * 获取一个指定坐标的格子。
     * 注意请不要给一个棋盘之外的坐标哦~
     *
     * @param x 第x列
     * @param y 第y行
     * @return 该坐标的格子
     */
    public SingleComponent getGrid(int x, int y) {
        try {
            return mineField[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public int[][] getChessboard() {
        return chessboard;
    }

    public SingleComponent[][] getMineField() {
        return mineField;
    }

    public int getxCount() {
        return xCount;
    }

    public int getyCount() {
        return yCount;
    }

    public int[][] getCurrentState() {
        return currentState;
    }

    public int getMineCount() {
        return mineCount;
    }

    /**
     * 本方法用于对格子的状态进行时刻更新
     */
    public void updateCurrentState() {
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                switch (mineField[i][j].getStatus()) {
                    case Flag: {
                        currentState[i][j] = 1;
                        break;
                    }
                    case Wrong: {
                        currentState[i][j] = -2;
                        break;
                    }
                    case Covered: {
                        currentState[i][j] = 0;
                        break;
                    }
                    case Clicked: {
                        currentState[i][j] = 2;
                        break;
                    }
                    case Bombed: {
                        currentState[i][j] = -1;
                        break;
                    }
                }
            }
        }
    }


    //本方法用于将读取后格子们的打开状态符号重新赋予给格子们
    public void loadCurrentState() {
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                switch (InitialWindow.window.getCopyOfState().get(i).get(j)) {
                    case 0: {
                        mineField[i][j].setStatus(GridStatus.Covered);
                        break;
                    }
                    case -1: {
                        mineField[i][j].setStatus(GridStatus.Bombed);
                        break;
                    }
                    case 1: {
                        mineField[i][j].setStatus(GridStatus.Flag);
                        break;
                    }
                    case 2: {
                        mineField[i][j].setStatus(GridStatus.Clicked);
                        break;
                    }
                    case -2: {
                        mineField[i][j].setStatus(GridStatus.Wrong);
                        break;
                    }
                }
            }
        }
    }


    public void showMineDirectly() {
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                if (chessboard[i][j] == -1) {
                    mineField[i][j].setStatus(GridStatus.Show);
                }
            }
        }
    }

    public void backGame() {
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                switch (currentState[i][j]) {
                    case 0: {
                        mineField[i][j].setStatus(GridStatus.Covered);
                        break;
                    }
                    case -1: {
                        mineField[i][j].setStatus(GridStatus.Bombed);
                        break;
                    }
                    case 1: {
                        mineField[i][j].setStatus(GridStatus.Flag);
                        break;
                    }
                    case 2: {
                        mineField[i][j].setStatus(GridStatus.Clicked);
                        break;
                    }
                    case -2: {
                        mineField[i][j].setStatus(GridStatus.Wrong);
                        break;
                    }
                }
            }
        }
    }

    public void setMineField(SingleComponent[][] mineField) {
        this.mineField = mineField;
    }

    public void setChessboard(int[][] chessboard) {
        this.chessboard = chessboard;
    }


    public void reGame() {
        for (int i = 0; i < mineField.length; i++) {
            for (int j = 0; j < mineField[0].length; j++) {
                mineField[i][j].setStatus(GridStatus.Covered);
            }
        }
        updateCurrentState();
    }

    //递归开格子
    public void openCellForSingle(int i, int j) {

        if (!mineField[i][j].getStatus().equals(GridStatus.Covered)) {
            return;
        }
        if (chessboard[i][j] == 0) {
            System.out.println("dsg");
            mineField[i][j].setStatus(GridStatus.Clicked);
            if (i > 0 && chessboard[i - 1][j] == 0) {
                openCellForSingle(i - 1, j);
            } else if (i > 0 && chessboard[i - 1][j] != -1) {
                mineField[i - 1][j].setStatus(GridStatus.Clicked);
            }
            if (j > 0 && chessboard[i][j - 1] == 0) {
                openCellForSingle(i, j - 1);
            } else if (j > 0 && chessboard[i][j - 1] != -1) {
                mineField[i][j - 1].setStatus(GridStatus.Clicked);
            }
            if (j < yCount - 1 && chessboard[i][j + 1] == 0) {
                openCellForSingle(i, j + 1);
            } else if (j < yCount - 1 && chessboard[i][j + 1] != -1) {
                mineField[i][j + 1].setStatus(GridStatus.Clicked);
            }
            if (i < xCount - 1 && chessboard[i + 1][j] == 0) {
                openCellForSingle(i + 1, j);
            } else if (i < xCount - 1 && chessboard[i + 1][j] != -1) {
                mineField[i + 1][j].setStatus(GridStatus.Clicked);
            }
            if (i > 0 && j > 0 && chessboard[i - 1][j - 1] == 0) {
                openCellForSingle(i - 1, j - 1);
            } else if (i > 0 && j > 0 && chessboard[i - 1][j - 1] != -1) {
                mineField[i - 1][j - 1].setStatus(GridStatus.Clicked);
            }
            if (i > 0 && j < yCount - 1 && chessboard[i - 1][j + 1] == 0) {
                openCellForSingle(i - 1, j + 1);
            } else if (i > 0 && j < yCount - 1 && chessboard[i - 1][j + 1] != -1) {
                mineField[i - 1][j + 1].setStatus(GridStatus.Clicked);
            }
            if (j > 0 && i < xCount - 1 && chessboard[i + 1][j - 1] == 0) {
                openCellForSingle(i + 1, j - 1);
            } else if (j > 0 && i < xCount - 1 && chessboard[i + 1][j - 1] != -1) {
                mineField[i + 1][j - 1].setStatus(GridStatus.Clicked);
            }
            if (j < yCount - 1 && i < xCount - 1 && chessboard[i + 1][j + 1] == 0) {
                openCellForSingle(i + 1, j + 1);
            } else if (j < yCount - 1 && i < xCount - 1 && chessboard[i + 1][j + 1] != -1) {
                mineField[i + 1][j + 1].setStatus(GridStatus.Clicked);
            }
        }
    }


}

