const express = require("express");
const axios = require("axios");
const router = express.Router();

const API_BASE = "http://localhost:8080/v1"; // endpoint do backend

// Tela inicial
router.get("/", (req, res) => {
  res.render("index");
});

// Form de criação de agendamento
router.get("/agendamento", (req, res) => {
  res.render("agendamento", { response: null });
});

// Submeter agendamento
router.post("/agendamento", async (req, res) => {
  try {
    const toOffset = (val) => {
      if (!val) return null;
      // adiciona segundos e o offset local (-03:00)
      return `${val}:00-03:00`;
    };

    const payload = {
      valor: parseFloat(req.body.valor),
      moeda: req.body.moeda,
      chavePagador: req.body.chavePagador,
      nomePagador: req.body.nomePagador,
      chaveRecebedor: req.body.chaveRecebedor,
      nomeRecebedor: req.body.nomeRecebedor,
      periodicidade: req.body.periodicidade,
      dataInicio: toOffset(req.body.dataInicio),
      dataFim: toOffset(req.body.dataFim)
    };

    const response = await axios.post(`${API_BASE}/agendamentos`, payload);
    res.render("agendamento", { response: response.data });
  } catch (error) {
    console.error(error.response?.data || error.message);
    res.render("agendamento", { response: { error: error.response?.data || error.message } });
  }
});

// Listar agendamentos
router.get("/listar-agendamentos", async (req, res) => {
  try {
    const response = await axios.get(`${API_BASE}/agendamentos`);
    res.render("listar-agendamentos", { agendamentos: response.data });
  } catch (error) {
    res.send(error.message);
  }
});

// Consultar agendamento único
router.get("/agendamento/:id", async (req, res) => {
  try {
    const response = await axios.get(`${API_BASE}/agendamentos/${req.params.id}`);
    res.render("listar-agendamentos", { agendamentos: [response.data] });
  } catch (error) {
    res.send(error.message);
  }
});

// Tela de auditorias
router.get("/auditorias", async (req, res) => {
  try {
    const { tipoEvento, idCorrelacao } = req.query;
    let url = `http://localhost:8080/api/v1/auditorias?page=0&size=50`;

    if (tipoEvento && idCorrelacao) {
      url = `http://localhost:8080/api/v1/auditorias/agendamento-evento?idCorrelacao=${idCorrelacao}&tipoEvento=${tipoEvento}&page=0&size=50`;
    } else if (tipoEvento) {
      url = `http://localhost:8080/api/v1/auditorias/evento?tipoEvento=${tipoEvento}&page=0&size=50`;
    } else if (idCorrelacao) {
      url = `http://localhost:8080/api/v1/auditorias/agendamento?idCorrelacao=${idCorrelacao}&page=0&size=50`;
    }

    const response = await axios.get(url);
    res.render("auditorias", { auditorias: response.data.content, tipoEvento: tipoEvento || "", idCorrelacao: idCorrelacao || "" });
  } catch (error) {
    res.send(error.message);
  }
});

module.exports = router;
