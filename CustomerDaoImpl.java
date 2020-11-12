package lab_assign;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {

	public Connection connection;

	public CustomerDaoImpl() {
		connection = ConnectionFactory.getConnection();
	}

	@Override
	public void addCustomer(Customer customer) {
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"insert into customers(id,name,phone,email,dob,address,purchase_capacity) values (?,?,?,?,?,?,?)");
			pstmt.setInt(1, customer.getId());
			pstmt.setString(2, customer.getName());
			pstmt.setString(3, customer.getPhone());
			pstmt.setString(4, customer.getEmail());
			pstmt.setDate(5, new Date(customer.getDob().getTime()));
			pstmt.setString(6, customer.getAddress());
			pstmt.setDouble(7, customer.getPurchase_capacity());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteCustomer(int id) {
		Optional<Customer> customer = getCustomerById(id);
		try {
			PreparedStatement pstmt = connection.prepareStatement("delete from customers where id=?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCustomer(int id, double purchase_capacity) {
		Optional<Customer> customer = getCustomerById(id);
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("update customers set purchase_capacity=? where id=?");
			pstmt.setDouble(1, purchase_capacity);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Customer> getAllCustomer() {
		List<Customer> customers = new ArrayList<>();
		Customer customer = null;
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from customers");

			while (rs.next()) {

				customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"),
						rs.getString("email"), rs.getDate("dob"), rs.getString("address"),
						rs.getDouble("purchase_capacity"));

				customers.add(customer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customers;

	}

	@Override
	public Optional<Customer> getCustomerById(int id) {
		Customer customer = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement("select * from customers where id=?");
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"),
						rs.getString("email"), rs.getDate("dob"), rs.getString("address"),
						rs.getDouble("purchase_capacity"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(customer);
	}

	@Override
	public List<Customer> getSelectedCustomer(double purchase_capacity) {
		List<Customer> customers = new ArrayList<>();
		Customer customer = null;
		try {
			PreparedStatement pstmt = connection.prepareStatement("select * from customers where purchase_capacity>?");
			pstmt.setDouble(1, purchase_capacity);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"),
						rs.getString("email"), rs.getDate("dob"), rs.getString("address"),
						rs.getDouble("purchase_capacity"));
				customers.add(customer);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

}
