<#attempt>
<#include '/social_widget_logged.ftl'>
<#include '/social_widget_context.ftl'>
<#assign selectedContext = context!>
<#assign selectdAlias = communityAlias!>
<#if selectedContext == 'contextCommunity'>
	<#assign alias = selectdAlias>
<#else>
	<#assign alias = social.alias>
</#if>

<#assign params = '{instanceId: ${instanceId?c}, socialType: "${social.type!"USER"}", alias: "${alias!""}", selectedContext: "${selectedContext}" }'?html>
<script type="text/javascript" src="/webdesk/vcXMLRPC.js"></script>
<div class="wcm-widget-class wcm-widget-post-share super-widget" data-params="CustomPoster.instance(${params})" id="customposter_${instanceId}">
	<div class="wcm-widget-post-loading">
		<div class="wcm-widget-post-wrapper"></div>
	</div>
	<script type="text/template" class="tpl_social_poster">
		<!-- Verifica se o recurso está bloqueado -->
		{{#socialStateBlocked}}
			<!-- Verifica se tipo é comunidade -->
			{{#socialTypeCommunity}}
				<p class="message-information">${i18n.getTranslation('community.disabled')}</p>
			{{/socialTypeCommunity}}
			<!-- Verifica se tipo é usuário -->
			{{#socialTypeUser}}
				<p class="message-information">${i18n.getTranslation('user.disabled')}</p>
			{{/socialTypeUser}}
		{{/socialStateBlocked}}
		<!-- Se o recurso não está bloqueado faz carregamento dos elementos da social poster -->
		{{#socialStateActive}}
			<!-- Verifica se existe um alias -->
			{{#socialAlias}}
				<!-- Verifica se usuário está em sua própria página ou se usuário é membro da comunidade -->
				{{#isUserOwnPageOrMember}}
				<div class="poster-context-config" style="clear:both">
					<!-- Área da imagem do usuário ou comunidade -->
					<div class="fluig-style-guide">
						<figure class="user-avatar-container">
							<img data-update-image-profile="{{socialAlias}}" data-image-size="SMALL_PICTURE" src="/social/api/rest/social/image/profile/{{socialAlias}}/SMALL_PICTURE" class="fluig-style-guide img-rounded thumb-profile thumb-profile-sm thumb-profile-sm-legacy">
						</figure>
					</div>
					<form method="post" class="totvs-form grid post-share-form" data-post-share-form>
						<fieldset class="col-1">
							<!-- Área de postagem do conteúdo -->
							<div class="post-area" data-post-area>
								<div class="post-share-text-container">
									<span class="close-post-share byyou-controller byyou-controller-close3" data-close-post-share></span>
									<textarea name="post-share-text" class="post-share-text" id="post-share-text" placeholder="${i18n.getTranslation('share.question')}" data-post-share-text></textarea>
								</div>
								<!-- Verifica se tipo é comunidade -->
								{{#socialTypeCommunity}}
									<nav class="post-options-container fluig-style-guide">
										<!-- Habilita link de seleção de imagem se recurso estiver disponível -->
										{{#photoIsEnabled}}
											<a href="#" class="fluigicon fluigicon-picture-portrait fluigicon-md post-options post-photo" title="${i18n.getTranslation('post.picture')}" data-attach-media="photo"></a>
										{{/photoIsEnabled}}
										<!-- Habilita link de seleção de vídeo se recurso estiver disponível -->
										{{#videoIsEnabled}}
											<a href="#" class="fluigicon fluigicon-player-portrait fluigicon-md post-options post-video" title="${i18n.getTranslation('post.video')}" data-attach-media="video"></a>
										{{/videoIsEnabled}}
										<!-- Habilita link de seleção de documento se recurso estiver disponível -->
										{{#documentIsEnabled}}
											<a href="#" class="fluigicon fluigicon-file fluigicon-md post-options post-document" title="${i18n.getTranslation('post.document')}" data-attach-media="document"></a>
										{{/documentIsEnabled}}
									</nav>
								{{/socialTypeCommunity}}
							</div>
						</fieldset>
						<fieldset class="col-1">
							<div class="submit-area group form-inline">
								<!-- Área da lista de restrição do post e do botão de envio -->
								{{#canConfigActions}}
									<div class="post-submit-type fr">
										<div class="fluig-style-guide fl" style="margin-right: 20px;">
											<p class="fs-no-margin" style="padding-left: 5px;">Ações disponíveis:</p>
											<ul class="list-inline fs-no-margin">
												<li>
													<div class="checkbox fs-no-margin-bottom">
														<label>
															<input type="checkbox" data-poster-can="like"> <span class="fluigicon fluigicon-thumbs-up fluigicon-sm"></span>
														</label>
													</div>
												</li>
												<li>
													<div class="checkbox fs-no-margin-bottom">
														<label>
															<input type="checkbox" data-poster-can="comment"> <span class="fluigicon fluigicon-comment fluigicon-sm"></span>
														</label>
													</div>
												</li>
												<li>
													<div class="checkbox fs-no-margin-bottom">
														<label>
															<input type="checkbox" data-poster-can="watch"> <span class="fluigicon fluigicon-bell-empty fluigicon-sm"></span>
														</label>
													</div>
												</li>
												<li>
													<div class="checkbox fs-no-margin-bottom">
														<label>
															<input type="checkbox" data-poster-can="share"> <span class="fluigicon fluigicon-share fluigicon-sm"></span>
														</label>
													</div>
												</li>
												<li>
													<div class="checkbox fs-no-margin-bottom">
														<label>
															<input type="checkbox" data-poster-can="denounce"> <span class="fluigicon fluigicon-flag fluigicon-sm"></span>
														</label>
													</div>
												</li>
											</ul>
										</div>
<div class="post-submit-type fr" style="height: 53px;line-height: 53px;">
											<div class="fluig-style-guide fl">
												<p class="fs-no-margin" style="padding-left: 5px;">Prioridade da notificação:</p>
												  <div class="list-inline fs-no-margin">
													  <select class="form-group" data-priority>
													  	<option value="NORMAL">Normal</option>
														<option value="HIGH">Alto</option>
														<option value="NONE">Não Enviar</option>
													  </select>
												  </div>
											</div>
									{{/canConfigActions}}
									<div class="fr" style="height: 53px;line-height: 53px;">
										<span class="post-text-limit">600</span>
			                            <!-- Se o recurso está bloqueado desabilita botão -->
										{{#socialStateBlocked}}
											<button class="totvs-btn-disabled" disabled="disabled">${i18n.getTranslation('profile.disabled')}</button>
										{{/socialStateBlocked}}
										<!-- Se o recurso não está bloqueado exibe botão -->
										{{#notSocialStateBlocked}}
											<button type="submit" class="totvs-btn-disabled post-share-submit" disabled="disabled">${i18n.getTranslation('publish')}</button>
										{{/notSocialStateBlocked}}
									</div>
								</div>
							</div>
						</fieldset>
					</form>
					<!-- Monta a mensagem de contexto caso configurado -->
					{{^isContextUndefined}}
						<#-- Style inline (margin left) até migração da poster para fluig-style-guide -->
						<div class="fluig-style-guide" style="margin-left:52px">
							<p class="text-primary">
								${i18n.getTranslation("poster.shared.with")}
								{{#isContextFollowers}}
									${i18n.getTranslation("poster.my.followers")}
								{{/isContextFollowers}}

								{{#isContextCommunities}}
									<a href="${tenantURI!''}/subject/${communityAlias!''}"> ${communityName!''}</a>
								{{/isContextCommunities}}
							</p>
						</div>
					{{/isContextUndefined}}
				</div>
				{{/isUserOwnPageOrMember}}
			{{/socialAlias}}
		{{/socialStateActive}}
	</script>

	<script type="text/template" class="tpl_can_not_access">
		<div class="fluig-style-guide">
			<div class="alert alert-warning" role="alert">
				<h3 style="margin-top:0;" ><strong>${i18n.getTranslation('unavailable.poster')}</strong></h3>
				${i18n.getTranslation('contact.administrator')}
			</div>
		</div>
	</script>

	<script type="text/template" class="tpl_member_not_in_group">
		<div class="fluig-style-guide">
			<div class="alert alert-warning" role="alert">
				<h3 style="margin-top:0;" ><strong>${i18n.getTranslation('unavailable.poster')}</strong></h3>
				${i18n.getTranslation('user.not.participant')}<a href="${tenantURI!''}/subject/${communityAlias!''}">${communityName!''}</a>
			</div>
		</div>
	</script>

	<script type="text/template" class="tpl_no_content">
		<!-- Não eliminar! Utilizado para remover conteúdo na exibição quando necessário. -->
	</script>
	
</div>
<#recover>
	<#include "/social_error.ftl">
</#attempt>
