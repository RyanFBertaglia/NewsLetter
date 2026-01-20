import { apiAvaliar } from './api';

export async function getVersion(version) {
  try {
    const response = await apiAvaliar.get('/getRate', {
      params: {
        version: version
      }
    });
    return response.data;
  } catch (error) {
    console.error('Erro ao obter versão:', error);
    throw error;
  }
}


export async function createComment(commentData) {
  try {
    const { message, idUser, version } = commentData;

    if (!message || !idUser || !version) {
      throw new Error('Todos os campos são obrigatórios: message, idUser, version');
    }

    const response = await apiAvaliar.post('/sendComment',
      { message, version },
      {
        params: {
          idUser
        }
      }
    );

    return response.data;
  } catch (error) {
    console.error('Erro ao criar comentário:', error);

    if (error.response) {
      switch (error.response.status) {
        case 400:
          throw new Error('Dados inválidos. Verifique os campos.');
        case 409:
          throw new Error('Você já respondeu este questionário.');
        case 401:
          throw new Error('Não autorizado. Faça login novamente.');
        case 404:
          throw new Error('Questionário não encontrado.');
        default:
          throw new Error(`Erro do servidor: ${error.response.data || error.response.status}`);
      }
    } else if (error.request) {
      throw new Error('Não foi possível conectar ao servidor.');
    } else {
      throw new Error('Erro ao processar requisição: ' + error.message);
    }
  }
}

