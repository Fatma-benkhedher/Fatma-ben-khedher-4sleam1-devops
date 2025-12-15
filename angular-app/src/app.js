const API_LIST = "/student/Department/getAllDepartment";
const API_DELETE = (id) => `/student/Department/deleteDepartment/${id}`;
const HEALTH = "/student/actuator/health";

const apiStatusEl = document.getElementById("api-status");
const loadingEl = document.getElementById("loading");
const errorEl = document.getElementById("error-message");
const tbody = document.getElementById("departments-tbody");
const refreshBtn = document.getElementById("refresh-btn");

function showError(msg) {
  errorEl.style.display = "block";
  errorEl.textContent = msg;
}
function clearError() {
  errorEl.style.display = "none";
  errorEl.textContent = "";
}
function setLoading(v) {
  loadingEl.style.display = v ? "block" : "none";
}

async function checkHealth() {
  try {
    const r = await fetch(HEALTH);
    const ok = r.ok;
    apiStatusEl.textContent = ok ? "Connecté" : "Déconnecté";
    apiStatusEl.style.color = ok ? "green" : "crimson";
  } catch {
    apiStatusEl.textContent = "Déconnecté";
    apiStatusEl.style.color = "crimson";
  }
}

function render(list) {
  tbody.innerHTML = "";
  if (!list || list.length === 0) {
    tbody.innerHTML = `<tr><td colspan="6">Aucun département</td></tr>`;
    return;
  }

  for (const d of list) {
    const id = d.idDepartment ?? "";
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${id}</td>
      <td>${d.name ?? ""}</td>
      <td>${d.location ?? ""}</td>
      <td>${d.phone ?? ""}</td>
      <td>${d.head ?? ""}</td>
      <td>
        <button data-id="${id}" class="btn btn-secondary">Supprimer</button>
      </td>
    `;
    tbody.appendChild(tr);
  }

  tbody.querySelectorAll("button[data-id]").forEach(btn => {
    btn.addEventListener("click", async () => {
      const id = btn.getAttribute("data-id");
      try {
        await fetch(API_DELETE(id), { method: "DELETE" });
        await loadDepartments();
      } catch (e) {
        console.error(e);
        showError("Erreur lors de la suppression ❌");
      }
    });
  });
}

async function loadDepartments() {
  setLoading(true);
  clearError();
  await checkHealth();

  try {
    const r = await fetch(API_LIST);
    if (!r.ok) throw new Error(`HTTP ${r.status}`);
    const data = await r.json();
    render(data);
  } catch (e) {
    console.error(e);
    showError("Erreur lors du chargement des départements ❌");
  } finally {
    setLoading(false);
  }
}

refreshBtn.addEventListener("click", loadDepartments);
loadDepartments();

