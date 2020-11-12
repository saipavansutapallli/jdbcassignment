package lab_assign;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CustomerController {

	public static void main(String[] args) {
		Connection connection = ConnectionFactory.getConnection();
		if (connection != null)
			System.out.println("successfully connected");

		CustomerDao dao = new CustomerDaoImpl();
		Customer customer = new Customer(7, "pavan", "8978959412", "saipavan@gmail.com", new Date(), "amp", 70000);
		dao.addCustomer(customer);

	}

}
