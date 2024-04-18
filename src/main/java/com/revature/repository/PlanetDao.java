package com.revature.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {
    
    public List<Planet> getAllPlanets() {
		// TODO: implement
		try(Connection connection = ConnectionUtil.createConnection()){
			List<Planet> planetList = new ArrayList<>();
			String sql = "SELECT * FROM planets";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()){
				Planet possiblePlanet = new Planet();
				possiblePlanet.setId(rs.getInt(1));
				possiblePlanet.setName(rs.getString("name"));
				possiblePlanet.setOwnerId(rs.getInt(3));

				planetList.add(possiblePlanet);
			}

			return planetList;


		} catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Planet getPlanetByName(String planetName) {
		// TODO: implement
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM planets WHERE name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, planetName);
			Planet possiblePlanet = new Planet();
			ResultSet rs = ps.executeQuery();

			if (rs.next()){
				possiblePlanet.setName(rs.getString("name"));
				return possiblePlanet;
			}

			else
				return null;

		} catch(SQLException e){
			e.printStackTrace();
		}
		return null;			
	}

	public Planet getPlanetById(int planetId) {
		// TODO: implement
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "SELECT * FROM planets WHERE id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			Planet possiblePlanet = new Planet();
			ResultSet rs = ps.executeQuery();

			if (rs.next()){
				possiblePlanet.setId(rs.getInt("id"));
				possiblePlanet.setName(rs.getString(2));
				possiblePlanet.setOwnerId(rs.getInt(3));
			}

			return possiblePlanet;


		} catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Planet createPlanet(Planet p) {
		// TODO: implement
		try(Connection connection = ConnectionUtil.createConnection()){
			List<Planet> planetList = getAllPlanets();
			String sql = "INSERT INTO planets (name, ownerID) VALUES (?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getName());

			ps.setInt(2, p.getOwnerId());
			ps.executeUpdate();
			Planet possiblePlanet = new Planet();
			possiblePlanet.setName(p.getName());
			possiblePlanet.setOwnerId(p.getOwnerId());
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()){
				possiblePlanet.setId(rs.getInt(1));
			}

			if (rs.next()){
				possiblePlanet.setName(rs.getString("name"));
				return possiblePlanet;
			}

			else
				return null;

		} catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public boolean deletePlanetById(int planetId) {
		// TODO: implement
		try(Connection connection = ConnectionUtil.createConnection()){
			String sql = "DELETE FROM planets WHERE id = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, planetId);
			ps.executeUpdate();

			return true;

		} catch(SQLException e){
			e.printStackTrace();
		}
		return false;	}
}
