/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Assert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public final class RSAUtils {

	private static final String KEY_ALGORITHM = "RSA";

	private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

	private static final Provider PROVIDER = new BouncyCastleProvider();

	private RSAUtils() {
	}

	public static KeyPair generateKeyPair(int keySize) {
		Assert.state(keySize > 0);

		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM, PROVIDER);
			keyPairGenerator.initialize(keySize);
			return keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static PrivateKey generatePrivateKey(byte[] encodedKey) {
		Assert.notNull(encodedKey);

		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
			return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static PrivateKey generatePrivateKey(String keyString) {
		Assert.hasText(keyString);

		return generatePrivateKey(Base64.decodeBase64(keyString));
	}

	public static PublicKey generatePublicKey(byte[] encodedKey) {
		Assert.notNull(encodedKey);

		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
			return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static PublicKey generatePublicKey(String keyString) {
		Assert.hasText(keyString);

		return generatePublicKey(Base64.decodeBase64(keyString));
	}

	public static String getKeyString(Key key) {
		Assert.notNull(key);

		return Base64.encodeBase64String(key.getEncoded());
	}

	public static Key getKey(String type, InputStream inputStream, String password) {
		Assert.hasText(type);
		Assert.notNull(inputStream);

		try {
			KeyStore keyStore = KeyStore.getInstance(type, PROVIDER);
			keyStore.load(inputStream, password != null ? password.toCharArray() : null);
			String alias = keyStore.aliases().hasMoreElements() ? keyStore.aliases().nextElement() : null;
			return keyStore.getKey(alias, password != null ? password.toCharArray() : null);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (CertificateException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (UnrecoverableKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Certificate getCertificate(String type, InputStream inputStream) {
		Assert.hasText(type);
		Assert.notNull(inputStream);

		try {
			CertificateFactory certificateFactory = CertificateFactory.getInstance(type, PROVIDER);
			return certificateFactory.generateCertificate(inputStream);
		} catch (CertificateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static byte[] sign(String algorithm, PrivateKey privateKey, byte[] data) {
		Assert.hasText(algorithm);
		Assert.notNull(privateKey);
		Assert.notNull(data);

		try {
			Signature signature = Signature.getInstance(algorithm, PROVIDER);
			signature.initSign(privateKey);
			signature.update(data);
			return signature.sign();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (SignatureException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static boolean verify(String algorithm, PublicKey publicKey, byte[] sign, byte[] data) {
		Assert.hasText(algorithm);
		Assert.notNull(publicKey);
		Assert.notNull(sign);
		Assert.notNull(data);

		try {
			Signature signature = Signature.getInstance(algorithm, PROVIDER);
			signature.initVerify(publicKey);
			signature.update(data);
			return signature.verify(sign);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (SignatureException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static boolean verify(String algorithm, Certificate certificate, byte[] sign, byte[] data) {
		Assert.hasText(algorithm);
		Assert.notNull(certificate);
		Assert.notNull(sign);
		Assert.notNull(data);

		try {
			Signature signature = Signature.getInstance(algorithm, PROVIDER);
			signature.initVerify(certificate);
			signature.update(data);
			return signature.verify(sign);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (SignatureException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static byte[] encrypt(PublicKey publicKey, byte[] data) {
		Assert.notNull(publicKey);
		Assert.notNull(data);

		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
		Assert.notNull(privateKey);
		Assert.notNull(data);

		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}