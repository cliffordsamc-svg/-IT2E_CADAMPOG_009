package com.scc.dao;

import com.scc.model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public boolean insert(Appointment a) throws SQLException {
        String sql = "INSERT INTO appointments (patient_name, doctor, consult_fee, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.patientName());
            ps.setString(2, a.doctor());
            ps.setDouble(3, a.consultFee());
            ps.setString(4, a.status());
            return ps.executeUpdate() == 1;
        }
    }

    public List<Appointment> findAll() throws SQLException {
        String sql = "SELECT id, patient_name, doctor, consult_fee, status FROM appointments ORDER BY id";
        List<Appointment> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor"),
                        rs.getDouble("consult_fee"),
                        rs.getString("status")));
            }
        }
        return list;
    }
}