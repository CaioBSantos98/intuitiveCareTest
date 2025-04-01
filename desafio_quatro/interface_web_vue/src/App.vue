<script setup>
  import { ref } from 'vue';

  const searchTerm = ref('');
  const results = ref([]);

  const handleSearch = async (event) => {
    event.preventDefault();
    const response = await fetch(`http://127.0.0.1:8000/search?query=${searchTerm.value}`);
    const data = await response.json();
    console.log(data);
    results.value = data.Resultados;
  };

  const clearSearch = (event) => {
    event.preventDefault();
    searchTerm.value = '';
    results.value = [];
  };
</script>

<template>
  <header class="header">
    <h1>Intuitive Care Interface Web</h1>
  </header>

  <main>
    <h2>Bem-vindo ao Intuitive Care Interface Web</h2>
    <form class="form-container">
      <label for="nome" class="label-container">Buscar em operadoras de saude ativas</label>
      <input type="text" id="nome" name="nome" class="input-container" v-model="searchTerm">
      <div class="button-container">
        <button type="submit" class="search-button" @click="handleSearch">Pesquisar</button>
        <button class="search-button" @click="clearSearch">Limpar</button>
      </div>
    </form>
      <div class="results-container">
      <h2>Resultados da busca</h2>
      <ul v-if="results.length > 0">
        <li v-for="result in results" :key="result.id">{{ result }}</li>
      </ul>
      <p v-else>Nenhum resultado encontrado</p>
    </div>
  </main>
</template>

<style scoped>
  .header {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  h2 {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
  }

  .form-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 16px;
  }

  .label-container {
    font-size: 18px;
  }
  
  .input-container {
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
    width: 300px;
  }

  .search-button {
    margin-top: 10px;
    background-color: #007bff;
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
  }

  .button-container {
    display: flex;
    gap: 16px;
  }

  .results-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    font-size: 18px;
  }
</style>

