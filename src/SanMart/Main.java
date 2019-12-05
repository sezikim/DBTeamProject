package SanMart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static SanMart.Constant.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("어디서 장을 봐야할지를 알려드립니다!");

        printCategorySelectMessage();

        Scanner input = new Scanner(System.in);
        int selectMenu = Integer.parseInt(input.nextLine());

        while (true) {
            switch (selectMenu) {
                case FOOD:
                    printFoodSelectMessage();

                    String category = null;

                    int selectClass = Integer.parseInt(input.nextLine());
                    switch (selectClass) {
                        case FRUIT_VEGETABLE_GRAIN:
                            category = DBQuery.;
                            break;
                        case MILK_COLD_FROZEN:
                            category = "c2";
                            break;
                        case PROCESSED_CONDIMENT:
                            category = "c3";
                            break;
                    }

                    printDatabaseFromQuery(category, new ProductInfo() {
                        @Override
                        public void printInfo(ResultSet resultSet) {
                            try {
                                System.out.printf("%s %s %lf\n",
                                        resultSet.getString("product_name"),
                                        resultSet.getString("madeBy"),
                                        resultSet.getDouble("costAvg"));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case NECESSITY:
                    printNecessitySelectMessage();
                    break;
                case CART:
                    break;
            }
        }
    }

    public static void printDatabaseFromQuery(String sqlQuery, ProductInfo productInfo) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:alpha.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet resultSet = getResultSetFromSqlQuery(statement, sqlQuery);
            while (resultSet.next()) {
                productInfo.printInfo(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static ResultSet getResultSetFromSqlQuery(Statement statement, String sqlQuery) {
        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    private static void printCategorySelectMessage() {
        System.out.println("메뉴를 고르시오.\n");
        System.out.println("\t1. 식료품\n");
        System.out.println("\t2. 생활용품\n");
        System.out.println("\t3. 장바구니\n");
        System.out.println();
    }

    private static void printFoodSelectMessage() {
        System.out.println("\n식료품 코너\n");
        System.out.println("카테고를 고르시오.\n");
        System.out.println("\t1. 과일, 채소, 곡류\n");
        System.out.println("\t2. 유제품, 냉장, 냉동\n");
        System.out.println("\t3. 가공식품, 조미료\n");
        System.out.println();
    }

    private static void printNecessitySelectMessage() {
        System.out.println("\n생활용품 코너\n");
        System.out.println("카테고를 고르시오.\n");
        System.out.println("\t1. 청소, 세탁, 주방 \n");
        System.out.println("\t2. 욕실, 화장실\n");
        System.out.println("\t3. 유아, 일회용품, 뷰티\n");
        System.out.println();
    }

    private void printProductToCartMessage() {
        System.out.println("상번호, 수량을 입력해서 장바구니에 추가하세요!");
        System.out.println("예) 1 4");
        System.out.println("다 골랐으면 0번을 눌러주세요.\n");
        System.out.println();
    }
}
