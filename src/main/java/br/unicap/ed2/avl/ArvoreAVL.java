package br.unicap.ed2.avl;

import br.unicap.ed2.base.AbstractArvoreBinariaDePesquisa;

public class ArvoreAVL extends AbstractArvoreBinariaDePesquisa<NoAVL> {

    @Override
    public NoAVL novoNode(int key) {
        
        return new NoAVL(key);
    }

    @Override
    public NoAVL inserir(int key) {
        NoAVL n = inserirNoHelper(raiz, key);
        NoAVL i = inserirNoHelper(n, key);
        verificarBalanceamento(i);

        return i;
    }
    
    public void verificarBalanceamento(NoAVL atual) {
		setFB(atual);
		int balanceamento = atual.getFB();

		if (balanceamento == -2) {

			if (altura(atual.getEsquerda().getEsquerda()) >= altura(atual.getEsquerda().getDireita())) {
				atual = rotacaoDireita(atual);

			} else {
				atual = duplaRotacaoEsquerdaDireita(atual);
			}

		} else if (balanceamento == 2) {

			if (altura(atual.getDireita().getDireita()) >= altura(atual.getDireita().getEsquerda())) {
				atual = rotacaoEsquerda(atual);

			} else {
				atual = duplaRotacaoDireitaEsquerda(atual);
			}
		}

		if (atual.getPai() != null) {
			verificarBalanceamento(atual.getPai());
		} else {
			this.raiz = atual;
		}
	}

    @Override
    public NoAVL procurar(int key) {
        return procurarNoHelper(raiz, key);
    }

    @Override
    public void deletar(int key) {
        raiz = deletarNoHelper(raiz, key);
        verificarBalanceamento(raiz);
    }
    
    
    public NoAVL rotacaoEsquerda(NoAVL inicial) {

		NoAVL direita = inicial.getDireita();
		direita.setPai(inicial.getPai());

		inicial.setDireita(direita.getEsquerda());

		if (inicial.getDireita() != null) {
			inicial.getDireita().setPai(inicial);
		}

		direita.setEsquerda(inicial);
		inicial.setPai(direita);

		if (direita.getPai() != null) {

			if (direita.getPai().getDireita() == inicial) {
				direita.getPai().setDireita(direita);
			
			} else if (direita.getPai().getEsquerda() == inicial) {
				direita.getPai().setEsquerda(direita);
			}
		}

		setFB(inicial);
		setFB(direita);

		return direita;
	}
    
    public NoAVL rotacaoDireita(NoAVL inicial) {

		NoAVL esquerda = inicial.getEsquerda();
		esquerda.setPai(inicial.getPai());

		inicial.setEsquerda(esquerda.getDireita());

		if (inicial.getEsquerda() != null) {
			inicial.getEsquerda().setPai(inicial);
		}

		esquerda.setDireita(inicial);
		inicial.setPai(esquerda);

		if (esquerda.getPai() != null) {

			if (esquerda.getPai().getDireita() == inicial) {
				esquerda.getPai().setDireita(esquerda);
			
			} else if (esquerda.getPai().getEsquerda() == inicial) {
				esquerda.getPai().setEsquerda(esquerda);
			}
		}

		setFB(inicial);
		setFB(esquerda);

		return esquerda;
	}
    
    public NoAVL duplaRotacaoEsquerdaDireita(NoAVL inicial) {
		inicial.setEsquerda(rotacaoEsquerda(inicial.getEsquerda()));
		return rotacaoDireita(inicial);
	}

	public NoAVL duplaRotacaoDireitaEsquerda(NoAVL inicial) {
		inicial.setDireita(rotacaoDireita(inicial.getDireita()));
		return rotacaoEsquerda(inicial);
	}
    
    private int altura(NoAVL atual) {
		if (atual == null) {
			return -1;
		}

		if (atual.getEsquerda() == null && atual.getDireita() == null) {
			return 0;
		
		} else if (atual.getEsquerda() == null) {
			return 1 + altura(atual.getDireita());
		
		} else if (atual.getDireita() == null) {
			return 1 + altura(atual.getEsquerda());
		
		} else {
			return 1 + Math.max(altura(atual.getEsquerda()), altura(atual.getDireita()));
		}
	}
    
    private void setFB(NoAVL no) {
		no.setFB(altura(no.getDireita()) - altura(no.getEsquerda()));
	}
}