package SanMart;

import javax.swing.*;

import java.sql.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import static SanMart.Constant.*;
import static SanMart.DBQuery.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("\n------------------------------------------------");
		System.out.println("어디서 장을 봐야할지를 알려드립니다!");
		System.out.println("------------------------------------------------\n");

		while (true) {
			printCategorySelectMessage();
			Scanner input = new Scanner(System.in);
			int selectMenu = Integer.parseInt(input.nextLine());
			System.out.println(selectMenu);

			String[] Nullarg = new String[] {};
			String query;
			int selectClass;

			switch (selectMenu) {
				case FOOD:
					query = null;
					printFoodSelectMessage();

					selectClass = Integer.parseInt(input.nextLine());
					switch (selectClass) {
						case FRUIT_VEGETABLE_GRAIN:
							query = ViewProductFruitVegetableGrain;
							break;
						case MILK_COLD_FROZEN:
							query = ViewProductMilkColdFrozen;
							break;
						case PROCESSED_CONDIMENT:
							query = ViewProductProcessedCondiment;
							break;
					}
                    System.out.println("\n------------------------------------------------\n");
					databaseProcess(query, Nullarg, new ExecuteQuery() {
						@Override
						public void processFromResultSet(ResultSet resultSet) {
							try {
								System.out.printf("%4d %-25s %-10s %.1f\n",
									resultSet.getInt("product_id"),
									resultSet.getString("product_name"),
									resultSet.getString("madeBy"),
									resultSet.getDouble("costAvg"));
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					});

					printProductToCartMessage();
					while (true) {

						String purchaseItem = input.nextLine();
						StringTokenizer tokenizer = new StringTokenizer(purchaseItem, " ");
						int itemNumber = Integer.parseInt(tokenizer.nextToken());
						if (itemNumber == NONE) {
							System.out.println("이전 메뉴로 돌아갑니다.\n");
							break;
						}

						int itemCount = Integer.parseInt(tokenizer.nextToken());
                        System.out.println("\n------------------------------------------------");
						System.out.printf("%d 상품, %d 개가 장바구니에 추가되었습니다.", itemNumber, itemCount);
                        System.out.println("\n------------------------------------------------\n");
						String[] arg = new String[] {Integer.toString(itemNumber), Integer.toString(itemCount)};
						databaseProcess(InsertCartSimple, arg, new ExecuteQuery() {
							@Override
							public void processFromResultSet(ResultSet resultSet) {
								databaseProcess(ViewCartSimple, Nullarg, new ExecuteQuery() {
									@Override
									public void processFromResultSet(ResultSet resultSet) {
										try {
											System.out.printf("4%d %-25s %4d\n",
												resultSet.getInt("product_id"),
												resultSet.getString("product_name"),
												resultSet.getInt("cart_count"));
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}
								});
                                System.out.println("\n------------------------------------------------\n");
							}
						});
					}
					break;
				case NECESSITY:
					query = null;
					printNecessitySelectMessage();

					selectClass = Integer.parseInt(input.nextLine());
					switch (selectClass) {
						case CLEAN_LAUNDRY_KITCHEN:
							query = ViewProductCleanLaundryKitchen;
							break;
						case BATHROOM_TOILET:
							query = ViewProductBathroomToilet;
							break;
						case BABY_BEAUTY_DISPOSABLE:
							query = ViewProductBabyBeautyDisposable;
							break;
					}
                    System.out.println("\n------------------------------------------------\n");
					databaseProcess(query, Nullarg, new ExecuteQuery() {
						@Override
						public void processFromResultSet(ResultSet resultSet) {
							try {
								System.out.printf("%4d %-25s %-10s %.1f\n",
									resultSet.getInt("product_id"),
									resultSet.getString("product_name"),
									resultSet.getString("madeBy"),
									resultSet.getDouble("costAvg"));
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					});

					printProductToCartMessage();
					while (true) {

						String purchaseItem = input.nextLine();
						StringTokenizer tokenizer = new StringTokenizer(purchaseItem, " ");
						int itemNumber = Integer.parseInt(tokenizer.nextToken());
						if (itemNumber == NONE) {
							System.out.println("이전 메뉴로 돌아갑니다.\n");
							break;
						}

						int itemCount = Integer.parseInt(tokenizer.nextToken());
                        System.out.println("\n------------------------------------------------");
						System.out.printf("%d 상품, %d 개가 장바구니에 추가되었습니다.", itemNumber, itemCount);
                        System.out.println("\n------------------------------------------------\n");
						String[] arg = new String[] {Integer.toString(itemNumber), Integer.toString(itemCount)};
						databaseProcess(InsertCartSimple, arg, new ExecuteQuery() {
							@Override
							public void processFromResultSet(ResultSet resultSet) {
								databaseProcess(ViewCartSimple, Nullarg, new ExecuteQuery() {
									@Override
									public void processFromResultSet(ResultSet resultSet) {
										try {
											System.out.printf("%d %25s %10d\n",
												resultSet.getInt("product_id"),
												resultSet.getString("product_name"),
												resultSet.getInt("cart_count"));
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}
								});
							}
						});
                        System.out.println("\n------------------------------------------------\n");
					}
					break;
				case CART:

					query = ViewCartDetail;
					printCartViewMessage();
					databaseProcess(query, Nullarg, new ExecuteQuery() {
						@Override
						public void processFromResultSet(ResultSet resultSet) {
							try {
								System.out.printf("%s %d %s %d\n",
									resultSet.getString("product_name"),
									resultSet.getInt("cart_count"),
									resultSet.getString("mart_name"),
									resultSet.getInt("total_price"));
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					});

					query = WhereToGo;
                    System.out.println("------------------------------------------------\n");
					databaseProcess(query, Nullarg, new ExecuteQuery() {
                        @Override
                        public void processFromResultSet(ResultSet resultSet) {
                            try {
                                System.out.printf("%10s %5d\n",
                                    resultSet.getString("mart_name"),
                                    resultSet.getInt("final_price"));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    System.out.println("\n------------------------------------------------\n");
                    System.out.println();
                    System.out.println("종료하려면 0, 메인으로 돌아가려면 1을 입력하세요.");
                    String keepGoing = input.nextLine();
                    if (Integer.parseInt(keepGoing) == MAIN) {
                        continue;
                    }
                    if (Integer.parseInt(keepGoing) == END) {
                        System.out.println("------------------------------------------------");
                        System.out.println("종료합니다!");
                        return;
                    }
					break;
			}
		}
	}

	public static void databaseProcess(String sqlQuery, String[] arg, ExecuteQuery executeQuery) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:alpha.db");
			Statement statement;
			if (arg.length == 0) {
				statement = connection.createStatement();
			} else {
				statement = connection.prepareStatement(sqlQuery);
			}
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			ResultSet resultSet = getResultSetFromSqlQuery(statement, arg, sqlQuery);
			if (resultSet != null) {
				while (resultSet.next()) {
					executeQuery.processFromResultSet(resultSet);
				}
			} else
				executeQuery.processFromResultSet(null);
			statement.close();
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

	private static ResultSet getResultSetFromSqlQuery(Statement statement, String[] arg, String sqlQuery) {
		ResultSet resultSet = null;
		if (arg.length == 0) {
			try {
				resultSet = statement.executeQuery(sqlQuery);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				int i;
				PreparedStatement preState = (PreparedStatement)statement;
				for (i = 1; i <= arg.length; i++) {
					preState.setString(i, arg[i - 1]);
				}
				preState.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		System.out.println("\n------------------------------------------------");
		System.out.println("식료품 코너");
		System.out.println("------------------------------------------------\n");
		System.out.println("카테고를 고르시오.\n");
		System.out.println("\t1. 과일, 채소, 곡류\n");
		System.out.println("\t2. 유제품, 냉장, 냉동\n");
		System.out.println("\t3. 가공식품, 조미료\n");
		System.out.println();
	}

	private static void printNecessitySelectMessage() {
		System.out.println("\n------------------------------------------------");
		System.out.println("생활용품 코너");
		System.out.println("------------------------------------------------\n");
		System.out.println("카테고를 고르시오.\n");
		System.out.println("\t1. 청소, 세탁, 주방 \n");
		System.out.println("\t2. 욕실, 화장실\n");
		System.out.println("\t3. 유아, 일회용품, 뷰티\n");
		System.out.println();
	}

	private static void printCartViewMessage() {
        System.out.println("\n장바구니 현황입니다.");
		System.out.println("------------------------------------------------");

	}

	private static void printProductToCartMessage() {
        System.out.println("\n------------------------------------------------\n");
		System.out.println("상품번호, 수량을 입력해서 장바구니에 추가하세요!");
		System.out.println("예) 1234 4\n");
		System.out.println("------------------------------------------------\n");
		System.out.println("다 골랐으면 0번을 눌러주세요.");
		System.out.println("\n------------------------------------------------\n");
	}
}