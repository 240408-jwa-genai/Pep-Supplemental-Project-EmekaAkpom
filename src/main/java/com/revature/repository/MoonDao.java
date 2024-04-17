package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

import javax.xml.transform.Result;

public class MoonDao {
    
    public List<Moon> getAllMoons() {
		// TODO: implement
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons";
			Statement stmt = connection.createStatement();
			List<Moon> moonList = new ArrayList<>();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));

				moonList.add(moon);

			}

			return moonList;

		} catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Moon getMoonByName(String moonName) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons WHERE name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, moonName);
			Moon moon = new Moon();
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));

			}
			return moon;
		} catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Moon getMoonById(int moonId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM moons WHERE id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			Moon moon = new Moon();
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));

			}
			return moon;
		} catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Moon createMoon(Moon m) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			List<Moon> moonList = getAllMoons();
			String sql = "INSERT INTO moons (name, myPlanetId) VALUES (?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, m.getName());
            for (Moon value : moonList) {
                if (m.getName().equals(value.getName())) {
					System.out.println("The moon named \"" + m.getName() + "\" already exists within the list.");
					return null;
				}
            }
			ps.setInt(2, m.getMyPlanetId());
			Moon moon = new Moon();
			ps.executeUpdate();
			moon.setName(m.getName());
			moon.setMyPlanetId(m.getMyPlanetId());
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()){
				moon.setId(rs.getInt(1));
			}
			return moon;
		} catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteMoonById(int moonId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "DELETE FROM moons WHERE id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);

			ps.executeUpdate();

			return true;
		} catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		// TODO: implement
		try (Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT m.id, m.name, m.myPlanetId FROM moons m JOIN planets p ON m.myPlanetID = p.id WHERE p.id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			List<Moon> moonList = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			if (rs.next()){
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));

				moonList.add(moon);

			}
			return moonList;
		} catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
