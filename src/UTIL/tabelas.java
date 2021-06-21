/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UTIL;

/**
 *
 * @author edson
 */
public class tabelas {
    /** 
     -- Table: public.tbl_cfop

-- DROP TABLE public.tbl_cfop;

CREATE TABLE public.tbl_cfop
(
  id_cfop integer NOT NULL DEFAULT nextval('tbl_cfop_id_cfop_seq'::regclass),
  sigla_cfop character varying(6),
  descricao_cfop character varying(100),
  status_cfop integer,
  registro_cfop_usuario integer,
  registro_cfop timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT tbl_cfop_pkey PRIMARY KEY (id_cfop)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_cfop
  OWNER TO postgres;
  * 
  * 
  * 
  * -- Table: public.tbl_cliente

-- DROP TABLE public.tbl_cliente;

CREATE TABLE public.tbl_cliente
(
  cliente_id integer NOT NULL DEFAULT nextval('tbl_cliente_cliente_id_unico_seq'::regclass),
  cliente_nome character varying(135) NOT NULL,
  cliente_cnpj character varying(20),
  cliente_inscricao character varying(60),
  cliente_descricao character varying(160),
  cliente_telefone character varying(25),
  cliente_endereco character varying(160),
  cliente_no character varying(25),
  cliente_cep character varying(25),
  cliente_complemento character varying(145),
  cliente_bairro character varying(135),
  cliente_cidade character varying(135),
  cliente_observacao character varying(180),
  cliente_registro timestamp with time zone NOT NULL DEFAULT now(),
  cliente_status integer,
  cliente_usuario_id integer,
  CONSTRAINT tbl_cliente_pkey PRIMARY KEY (cliente_id),
  CONSTRAINT tbl_cliente_cliente_usuario_id_fkey FOREIGN KEY (cliente_usuario_id)
      REFERENCES public.tbl_usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_cliente
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_empresa

-- DROP TABLE public.tbl_empresa;

CREATE TABLE public.tbl_empresa
(
  empresa_id integer NOT NULL DEFAULT nextval('tbl_empresa_empresa_id_unico_seq'::regclass),
  empresa_nome character varying(135) NOT NULL,
  empresa_cnpj character varying(20),
  empresa_inscricao character varying(60),
  empresa_descricao character varying(160),
  empresa_telefone character varying(25),
  empresa_endereco character varying(160),
  empresa_no character varying(25),
  empresa_cep character varying(25),
  empresa_complemento character varying(145),
  empresa_bairro character varying(135),
  empresa_cidade character varying(135),
  empresa_observacao character varying(180),
  empresa_registro timestamp with time zone NOT NULL DEFAULT now(),
  empresa_status integer,
  empresa_usuario_id integer,
  CONSTRAINT tbl_empresa_pkey PRIMARY KEY (empresa_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_empresa
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_exp_nota

-- DROP TABLE public.tbl_exp_nota;

CREATE TABLE public.tbl_exp_nota
(
  id integer NOT NULL DEFAULT nextval('tbl_exp_nota_id_seq'::regclass),
  data character varying(20),
  hora character varying(10),
  numero character varying(15),
  chave character varying(60),
  motorista character varying(80),
  placa character varying(15),
  status integer,
  exp_pedido integer,
  CONSTRAINT tbl_exp_nota_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_exp_nota
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_exp_protocolo

-- DROP TABLE public.tbl_exp_protocolo;

CREATE TABLE public.tbl_exp_protocolo
(
  id integer NOT NULL DEFAULT nextval('tbl_exp_protocolo_id_seq'::regclass),
  data character varying(20),
  hora character varying(10),
  numero character varying(15),
  chave character varying(60),
  motorista character varying(80),
  placa character varying(15),
  status integer,
  exp_pedido integer,
  CONSTRAINT tbl_exp_protocolo_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_exp_protocolo
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_fornecedor

-- DROP TABLE public.tbl_fornecedor;

CREATE TABLE public.tbl_fornecedor
(
  fornecedor_id integer NOT NULL DEFAULT nextval('tbl_fornecedor_fornecedor_id_unico_seq'::regclass),
  fornecedor_nome character varying(135) NOT NULL,
  fornecedor_cnpj character varying(20),
  fornecedor_inscricao character varying(60),
  fornecedor_descricao character varying(160),
  fornecedor_telefone character varying(25),
  fornecedor_endereco character varying(160),
  fornecedor_no character varying(25),
  fornecedor_cep character varying(25),
  fornecedor_complemento character varying(145),
  fornecedor_bairro character varying(135),
  fornecedor_cidade character varying(135),
  fornecedor_observacao character varying(180),
  fornecedor_registro timestamp with time zone NOT NULL DEFAULT now(),
  fornecedor_status integer,
  fornecedor_usuario_id integer,
  CONSTRAINT tbl_fornecedor_pkey PRIMARY KEY (fornecedor_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_fornecedor
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_item

-- DROP TABLE public.tbl_item;

CREATE TABLE public.tbl_item
(
  item_id integer NOT NULL DEFAULT nextval('tbl_item_item_id_seq'::regclass),
  item_produto_id integer,
  item_complemento character varying(50),
  item_qtd double precision,
  item_nota_id integer,
  item_protocolo_id integer,
  item_pedido_id integer,
  CONSTRAINT tbl_item_pkey PRIMARY KEY (item_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_item
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_item_temp

-- DROP TABLE public.tbl_item_temp;

CREATE TABLE public.tbl_item_temp
(
  id integer NOT NULL DEFAULT nextval('tbl_item_temp_id_seq'::regclass),
  t_produto_id integer,
  t_complemento character varying(50),
  t_qtd double precision,
  item_id_nota integer,
  t_item_st integer,
  t_item_id integer,
  item_id_protocolo integer,
  item_id_pedido integer,
  CONSTRAINT tbl_item_temp_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_item_temp
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_nota

-- DROP TABLE public.tbl_nota;

CREATE TABLE public.tbl_nota
(
  id_nota integer NOT NULL DEFAULT nextval('tbl_nota_id_nota_seq'::regclass),
  nota_id_cfop integer,
  nota_id_fornecedor integer,
  nota_numero character varying(10),
  nota_chave character varying(60),
  nota_observacao character varying(300),
  nota_total double precision,
  nota_hora character varying(10),
  nota_data character varying(20),
  nota_status integer,
  nota_id_usuario integer,
  nota_registro timestamp with time zone NOT NULL DEFAULT now(),
  nota_informacao character varying(400),
  CONSTRAINT tbl_nota_pkey PRIMARY KEY (id_nota)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_nota
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_pedido

-- DROP TABLE public.tbl_pedido;

CREATE TABLE public.tbl_pedido
(
  id_pedido integer NOT NULL DEFAULT nextval('tbl_pedido_id_pedido_seq'::regclass),
  pedido_id_cliente integer,
  pedido_observacao character varying(300),
  pedido_total double precision,
  pedido_hora character varying(10),
  pedido_data character varying(20),
  pedido_status integer,
  pedido_id_usuario integer,
  pedido_registro timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT tbl_pedido_pkey PRIMARY KEY (id_pedido)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_pedido
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_produto

-- DROP TABLE public.tbl_produto;

CREATE TABLE public.tbl_produto
(
  produto_id integer NOT NULL DEFAULT nextval('tbl_produto_produto_id_unico_seq'::regclass),
  produto_nome character varying(150),
  produto_unidade integer,
  produto_valor double precision,
  produto_ncm integer,
  produto_min double precision,
  produto_descricao character varying(180),
  produto_registro timestamp with time zone NOT NULL DEFAULT now(),
  produto_status integer,
  produto_usuario_id integer,
  produto_categoria integer,
  produto_informacao character varying(400),
  CONSTRAINT tbl_produto_pkey PRIMARY KEY (produto_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_produto
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_produto_imagem

-- DROP TABLE public.tbl_produto_imagem;

CREATE TABLE public.tbl_produto_imagem
(
  id_imagem integer NOT NULL DEFAULT nextval('tbl_produto_imagem_id_imagem_seq'::regclass),
  id_imagem_produto integer,
  caminho_imagem character varying(400),
  nome_imagem character varying(100),
  CONSTRAINT tbl_produto_imagem_pkey PRIMARY KEY (id_imagem)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_produto_imagem
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_produto_kit

-- DROP TABLE public.tbl_produto_kit;

CREATE TABLE public.tbl_produto_kit
(
  id_kits integer NOT NULL DEFAULT nextval('tbl_kits_id_kits_seq'::regclass),
  id_produto integer,
  produto_kit integer,
  CONSTRAINT tbl_kits_pkey PRIMARY KEY (id_kits)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_produto_kit
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_produto_unidade

-- DROP TABLE public.tbl_produto_unidade;

CREATE TABLE public.tbl_produto_unidade
(
  id_unidade integer NOT NULL DEFAULT nextval('tbl_unidade_id_unico_unidade_seq'::regclass),
  sigla_unidade character varying(6),
  descricao_unidade character varying(16),
  frag_unidade integer,
  status_unidade integer,
  registro_unidade_usuario integer,
  data timestamp without time zone,
  registro_unidade timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT tbl_unidade_pkey PRIMARY KEY (id_unidade),
  CONSTRAINT tbl_unidade_registro_unidade_usuario_fkey FOREIGN KEY (registro_unidade_usuario)
      REFERENCES public.tbl_usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_produto_unidade
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_protocolo

-- DROP TABLE public.tbl_protocolo;

CREATE TABLE public.tbl_protocolo
(
  id_protocolo integer NOT NULL DEFAULT nextval('tbl_protocolo_id_protocolo_seq'::regclass),
  protocolo_id_fornecedor integer,
  protocolo_numero character varying(10),
  protocolo_observacao character varying(300),
  protocolo_total double precision,
  protocolo_hora character varying(10),
  protocolo_data character varying(20),
  protocolo_status integer,
  protocolo_id_usuario integer,
  protocolo_registro timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT tbl_protocolo_pkey PRIMARY KEY (id_protocolo)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_protocolo
  OWNER TO postgres;

* 
* 
* -- Table: public.tbl_usuario

-- DROP TABLE public.tbl_usuario;

CREATE TABLE public.tbl_usuario
(
  id_usuario integer NOT NULL DEFAULT nextval('tbl_usuario_is_unico_usuario_seq'::regclass),
  permissao_usuario integer,
  nome_usuario character varying(60),
  login_usuario character varying(30),
  senha_usuario character varying(30),
  data_usuario date DEFAULT now(),
  registro_usuario timestamp with time zone NOT NULL DEFAULT now(),
  status_usuario integer,
  CONSTRAINT tbl_usuario_pkey PRIMARY KEY (id_usuario)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.tbl_usuario
  OWNER TO postgres;

     
     
     
     
     **/
    
    
    
    
}
